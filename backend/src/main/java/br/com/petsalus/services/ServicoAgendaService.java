package br.com.petsalus.services;

import br.com.petsalus.dtos.response.Agendamento;
import br.com.petsalus.dtos.response.MeusAgendamentos;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.*;
import br.com.petsalus.enums.RegistroMedicoStatus;
import br.com.petsalus.enums.ServicoAgendaStatus;
import br.com.petsalus.enums.TipoServico;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.AgendamentoMapper;
import br.com.petsalus.repositories.EmpresaEmpregadoRepository;
import br.com.petsalus.repositories.ServicoAgendaEmpregadoRepository;
import br.com.petsalus.repositories.ServicoAgendaRepository;
import br.com.petsalus.repositories.ServicoEmpregadoRepository;
import br.com.petsalus.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicoAgendaService {

    private final PetUtils petUtils;
    private final ServicoUtils servicoUtils;
    private final ServicoAgendaUtils servicoAgendaUtils;
    private final RegistroMedicoUtils registroMedicoUtils;
    private final CustomExceptionUtils customExceptionUtils;

    private final RetornoService retornoService;

    private final ServicoAgendaRepository servicoAgendaRepository;
    private final ServicoEmpregadoRepository servicoEmpregadoRepository;
    private final EmpresaEmpregadoRepository empresaEmpregadoRepository;
    private final ServicoAgendaEmpregadoRepository servicoAgendaEmpregadoRepository;

    public ResponseEntity<List<String>> buscarHorariosDisponiveis(Long servicoId,
                                                                  LocalDate data)
            throws CustomException {

        List<LocalTime> slots = calcularHorariosDisponiveis(servicoId, data);

        List<String> resposta = slots.stream()
                .map(t -> t.format(DateTimeFormatter.ofPattern("HH:mm")))
                .toList();

        return ResponseEntity.ok(resposta);
    }

    private List<LocalTime> calcularHorariosDisponiveis(Long servicoId,
                                                        LocalDate data)
            throws CustomException {

        Servico servico = servicoUtils.findById(servicoId);
        Empresa empresa = servico.getEmpresa();

        long duracaoMin   = servico.getTempoServicoMin();
        LocalTime abre    = empresa.getHoraAbertura();
        LocalTime fecha   = empresa.getHoraEncerramento();

        if (abre.plusMinutes(duracaoMin).isAfter(fecha))
            return List.of();

        Set<User> elegiveis = servicoEmpregadoRepository.findByServico(servico).stream()
                .map(ServicoEmpregado::getEmpregado)
                .filter(emp -> empresaEmpregadoRepository.existsByEmpresaAndEmpregado(empresa, emp))
                .collect(Collectors.toSet());

        if (elegiveis.isEmpty())
            throw customExceptionUtils.errorAndBadRequest(
                    "A empresa não possui empregados para este serviço.");

        LocalDateTime inicioDia = data.atTime(abre);
        LocalDateTime fimDia    = data.atTime(fecha);

        List<ServicoAgendaEmpregado> ocupacoes = servicoAgendaEmpregadoRepository
                .buscarEntreDatas(elegiveis, inicioDia, fimDia,
                        ServicoAgendaStatus.CANCELADO);

        Map<Long, List<Intervalo>> agendaPorEmpregado = ocupacoes.stream()
                .collect(Collectors.groupingBy(
                        sae -> sae.getEmpregado().getId(),
                        Collectors.mapping(sae -> {
                            LocalDateTime ini = sae.getServicoAgenda().getDataHora();
                            LocalDateTime fim = ini.plusMinutes(
                                    sae.getServicoAgenda()
                                            .getServico().getTempoServicoMin());
                            return new Intervalo(ini.toLocalTime(), fim.toLocalTime());
                        }, Collectors.toList())));

        /* --- grade dinâmica -------------------------------------------- */
        int gradeMin         = calcularGradeMinutos(empresa);      // ex.: 15/30
        Duration passo       = Duration.ofMinutes(gradeMin);
        Duration duracaoServ = Duration.ofMinutes(duracaoMin);

        List<LocalTime> vagas = new ArrayList<>();
        for (LocalTime slot = abre;
             !slot.plus(duracaoServ).isAfter(fecha);
             slot = slot.plus(passo)) {

            final LocalTime inicioSlot = slot;
            final LocalTime fimSlot    = slot.plus(duracaoServ);

            boolean existeLivre = elegiveis.stream().anyMatch(emp -> {
                List<Intervalo> busy = agendaPorEmpregado
                        .getOrDefault(emp.getId(), List.of());
                return busy.stream()
                        .noneMatch(i -> i.sobrepoe(inicioSlot, fimSlot));
            });

            if (existeLivre) vagas.add(inicioSlot);
        }
        return vagas;
    }

    /* --------------------- funções auxiliares ------------------------- */

    private int calcularGradeMinutos(Empresa empresa) {
        List<Long> duracoes = servicoEmpregadoRepository.findDistinctDurationsByEmpresa(empresa);
        return duracoes.stream()
                .mapToInt(Long::intValue)
                .reduce(this::mdc)
                .orElse(15);
    }

    private int mdc(int a, int b) {          // Euclides
        while (b != 0) { int t = b; b = a % b; a = t; }
        return a;
    }

    private record Intervalo(LocalTime ini, LocalTime fim) {
        boolean sobrepoe(LocalTime aIni, LocalTime aFim) {
            return aIni.isBefore(fim) && aFim.isAfter(ini);  // [ini,fim)
        }
    }

    public ResponseEntity<Retorno> registrar(Long servicoId,
                                             Long petId,
                                             LocalDateTime inicioSolicitado)
            throws CustomException {

        Servico servico = servicoUtils.findById(servicoId);
        Pet     pet     = petUtils.findById(petId);
        Empresa empresa = servico.getEmpresa();

        validarHorarioEmpresa(inicioSolicitado, servico.getTempoServicoMin(), empresa);

        // --- candidatos que fazem o serviço e pertencem à empresa -----------------
        Set<User> habilitados = servicoEmpregadoRepository.findByServico(servico).stream()
                .map(ServicoEmpregado::getEmpregado)
                .collect(Collectors.toSet());

        List<User> candidatos = empresaEmpregadoRepository.findAllByEmpresa(empresa).stream()
                .map(EmpresaEmpregado::getEmpregado)
                .filter(habilitados::contains)
                .toList();

        if (candidatos.isEmpty()) {
            throw customExceptionUtils.errorAndBadRequest("A empresa não possui empregados habilitados para este serviço.");
        }

        LocalDateTime fimSolicitado = inicioSolicitado.plusMinutes(servico.getTempoServicoMin());

        // --- procura o primeiro disponível ----------------------------------------
        for (User emp : candidatos) {
            boolean ocupado = servicoAgendaEmpregadoRepository.encontrarConflitos(
                            emp, inicioSolicitado, fimSolicitado, ServicoAgendaStatus.CANCELADO)
                    .stream()
                    .findAny()
                    .isPresent();

            if (!ocupado) {
                // cria agenda
                ServicoAgenda agenda = new ServicoAgenda();

                agenda.setPet(pet);
                agenda.setEmpregado(emp);
                agenda.setServico(servico);
                agenda.setDataHora(inicioSolicitado);
                agenda.setStatus(ServicoAgendaStatus.PENDENTE);
                agenda.setTipoAgendamento(RegistroMedicoStatus.ROTINA_AGENDAMENTO);

                servicoAgendaRepository.save(agenda);

                // vincula funcionário

                ServicoAgendaEmpregado sae = new ServicoAgendaEmpregado();

                sae.setServicoAgenda(agenda);
                sae.setEmpregado(emp);

                servicoAgendaEmpregadoRepository.save(sae);

                return retornoService.retornoSucesso("Agendamento criado: " + agenda.getId());
            }
        }

        throw customExceptionUtils.errorAndBadRequest("Nenhum empregado disponível para o horário solicitado.");
    }

    public ResponseEntity<Retorno> cancelar(Long servicoAgendaIdId) throws CustomException {
        
        ServicoAgenda servicoAgenda = servicoAgendaUtils.findById(servicoAgendaIdId);

        servicoAgenda.setStatus(ServicoAgendaStatus.CANCELADO);

        servicoAgendaRepository.save(servicoAgenda);

        log.info(" >>> Serviço Agendado cancelado com sucesso.");
        return retornoService.retornoSucesso("Serviço Agendado cancelado com sucesso.");
    }

    public ResponseEntity<Retorno> efetivar(Long servicoAgendaId) throws CustomException {

        ServicoAgenda servicoAgenda = servicoAgendaUtils.findById(servicoAgendaId);

        servicoAgenda.setStatus(ServicoAgendaStatus.EFETIVADO);

        servicoAgendaRepository.save(servicoAgenda);

        if (servicoAgenda.getServico().getTipoServico().equals(TipoServico.CLINICO)) {
            registroMedicoUtils.salvar(servicoAgenda.getPet(), servicoAgenda, servicoAgenda.getServico().getDescricao());
        }

        log.info(" >>> Serviço Agendado efetivado com sucesso.");
        return retornoService.retornoSucesso("Serviço Agendado efetivado com sucesso.");
    }

    public MeusAgendamentos meusAgendamentos(Long petId) throws CustomException {

        Pet pet = petUtils.findById(petId);

        List<ServicoAgenda> pendentes = servicoAgendaRepository.findByPetAndStatus(pet, ServicoAgendaStatus.PENDENTE);
        List<ServicoAgenda> cancelados = servicoAgendaRepository.findByPetAndStatus(pet, ServicoAgendaStatus.CANCELADO);
        List<ServicoAgenda> efetivados = servicoAgendaRepository.findByPetAndStatus(pet, ServicoAgendaStatus.EFETIVADO);

        List<Agendamento> pendentesMapped = AgendamentoMapper.map(pendentes);
        List<Agendamento> canceladosMapped = AgendamentoMapper.map(cancelados);
        List<Agendamento> efetivadosMapped = AgendamentoMapper.map(efetivados);

        log.info(" >>> Retornando lista de Agendamentos de Pet com sucesso.");
        return MeusAgendamentos.builder()
                .pendentes(pendentesMapped)
                .cancelados(canceladosMapped)
                .efetivados(efetivadosMapped)
                .build();
    }

    public ServicoAgenda registrarEmergencia(Pet pet, Servico servico, LocalDateTime dataHora, User veterinario) throws CustomException {

        ServicoAgenda servicoAgenda = new ServicoAgenda();

        servicoAgenda.setPet(pet);
        servicoAgenda.setServico(servico);
        servicoAgenda.setDataHora(dataHora);
        servicoAgenda.setEmpregado(veterinario);
        servicoAgenda.setStatus(ServicoAgendaStatus.EFETIVADO);
        servicoAgenda.setTipoAgendamento(RegistroMedicoStatus.EMERGENCIA);

        return servicoAgendaRepository.save(servicoAgenda);
    }

    private void validarHorarioEmpresa(LocalDateTime inicio,
                                       Long duracaoMin,
                                       Empresa empresa) throws CustomException {

        LocalTime horaInicio = inicio.toLocalTime();
        LocalTime horaFim    = horaInicio.plusMinutes(duracaoMin);

        if (horaInicio.isBefore(empresa.getHoraAbertura()) ||
                horaFim.isAfter(empresa.getHoraEncerramento())) {
            throw customExceptionUtils.errorAndBadRequest("Horário fora do expediente da empresa.");
        }
    }
}