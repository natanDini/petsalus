package br.com.petsalus.services;

import br.com.petsalus.dtos.response.AgendaEmpregado;
import br.com.petsalus.dtos.response.Empregado;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.*;
import br.com.petsalus.enums.TipoServico;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.AgendaEmpregadoMapper;
import br.com.petsalus.mappers.EmpregadoMapper;
import br.com.petsalus.repositories.EmpresaEmpregadoRepository;
import br.com.petsalus.repositories.ServicoAgendaEmpregadoRepository;
import br.com.petsalus.repositories.ServicoEmpregadoRepository;
import br.com.petsalus.utils.CustomExceptionUtils;
import br.com.petsalus.utils.EmpresaUtils;
import br.com.petsalus.utils.ServicoUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpresaEmpregadoService {

    private final UserUtils userUtils;
    private final EmpresaUtils empresaUtils;
    private final ServicoUtils servicoUtils;
    private final CustomExceptionUtils customExceptionUtils;

    private final RetornoService retornoService;

    private final ServicoEmpregadoRepository servicoEmpregadoRepository;
    private final EmpresaEmpregadoRepository empresaEmpregadoRepository;
    private final ServicoAgendaEmpregadoRepository servicoAgendaEmpregadoRepository;

    public ResponseEntity<Retorno> relacionarEmpregadoEmpresa(Long empresaId, String cpf) throws CustomException {

        User user = userUtils.findByCpf(cpf);
        Empresa empresa = empresaUtils.findById(empresaId);

        if (empresaEmpregadoRepository.existsByEmpresaAndEmpregado(empresa, user)) {
            throw customExceptionUtils.errorAndBadRequest("Empregado já cadastrado na empresa.");
        }

        if (empresaEmpregadoRepository.existsByEmpregado(user)){
            throw customExceptionUtils.errorAndBadRequest("Empregado já cadastrado em outra empresa.");
        }

        EmpresaEmpregado ee = new EmpresaEmpregado();

        ee.setEmpregado(user);
        ee.setEmpresa(empresa);

        empresaEmpregadoRepository.save(ee);

        log.info(" >>> Relacionando empregado a empresa com sucesso.");
        return retornoService.retornoSucesso("Relacionando empregado a empresa com sucesso.");
    }

    public List<Empregado> empregadosByEmpresa(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<User> empregados = empresaEmpregadoRepository.findEmpregadosByEmpresa(empresa);

        log.info(" >>> Retornando lista de empregados com sucesso.");
        return EmpregadoMapper.map(empregados);
    }

    public List<Empregado> colaboradoresByEmpresa(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<User> empregados = empresaEmpregadoRepository.findColaboradoresByEmpresa(empresa);

        log.info(" >>> Retornando lista de colaboradores com sucesso.");
        return EmpregadoMapper.map(empregados);
    }

    public List<Empregado> veterinariosByEmpresa(Long empresaId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);

        List<User> empregados = empresaEmpregadoRepository.findVeterinariosByEmpresa(empresa);

        log.info(" >>> Retornando lista de veterinarios com sucesso.");
        return EmpregadoMapper.map(empregados);
    }

    public List<AgendaEmpregado> agenda(Jwt jwt, LocalDate data) throws CustomException {

        User user = userUtils.findByJwt(jwt);

        LocalDateTime startOfDay = data.atStartOfDay();
        LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

        List<ServicoAgenda> agendamentos = servicoAgendaEmpregadoRepository.findAgendamentoByEmpregadoAndData(user, startOfDay, endOfDay);

        log.info(" >>> Retornando lista de agendamentos de empregado com sucesso.");
        return AgendaEmpregadoMapper.map(agendamentos);
    }

    public ResponseEntity<Retorno> vincularEmpregadoServico(Long empregadoId, Long servicoId) throws CustomException {

        User empregado = userUtils.findById(empregadoId);
        Servico servico = servicoUtils.findById(servicoId);

        if (servico.getTipoServico().equals(TipoServico.CLINICO) && empregado.getUserRole().equals(UserRole.COLABORADOR)){
            throw customExceptionUtils.errorAndBadRequest("Serviços do tipo clínico só podem ser realizados por veterinários.");
        }

        if (servico.getTipoServico().equals(TipoServico.PET_SHOP) && empregado.getUserRole().equals(UserRole.VETERINARIO)){
            throw customExceptionUtils.errorAndBadRequest("Serviços do tipo PetShop só podem ser realizados por colaboradores.");
        }

        if (servicoEmpregadoRepository.existsByServicoAndEmpregado(servico, empregado)){
            throw customExceptionUtils.errorAndBadRequest("Empregado já cadastrado para esse serviço.");
        }

        ServicoEmpregado se = new ServicoEmpregado();

        se.setServico(servico);
        se.setEmpregado(empregado);

        servicoEmpregadoRepository.save(se);

        log.info(" >>> Relacionando empregado a serviço com sucesso.");
        return retornoService.retornoSucesso("Relacionando empregado a serviço com sucesso.");
    }

    public List<Empregado> empregadosByServico(Long servicoId) throws CustomException {

        Servico servico = servicoUtils.findById(servicoId);

        List<User> empregados = servicoEmpregadoRepository.findEmpregadosByServico(servico);

        log.info(" >>> Retornando lista de empregados por serviço com sucesso.");
        return EmpregadoMapper.map(empregados);
    }

    public ResponseEntity<Retorno> retirarEmpregadoEmpresa(Long empresaId, Long empregadoId) throws CustomException {

        Empresa empresa = empresaUtils.findById(empresaId);
        User empregado = userUtils.findById(empregadoId);

        EmpresaEmpregado ee = empresaEmpregadoRepository.findByEmpresaAndEmpregado(empresa, empregado);

        empresaEmpregadoRepository.delete(ee);

        log.info(" >>> Retirando empregado da empresa com sucesso.");
        return retornoService.retornoSucesso("Retirando empregado da empresa com sucesso.");
    }

    public ResponseEntity<Retorno> retirarEmpregadoServico(Long servicoId, Long empregadoId) throws CustomException {

        User empregado = userUtils.findById(empregadoId);
        Servico servico = servicoUtils.findById(servicoId);

        ServicoEmpregado se = servicoEmpregadoRepository.findByServicoAndEmpregado(servico, empregado);

        servicoEmpregadoRepository.delete(se);

        log.info(" >>> Retirando empregado da serviço com sucesso.");
        return retornoService.retornoSucesso("Retirando empregado da serviço com sucesso.");
    }
}