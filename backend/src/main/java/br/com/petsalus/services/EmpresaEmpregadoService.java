package br.com.petsalus.services;

import br.com.petsalus.dtos.response.AgendaEmpregado;
import br.com.petsalus.dtos.response.Empregado;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.EmpresaEmpregado;
import br.com.petsalus.entities.ServicoAgenda;
import br.com.petsalus.entities.User;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.AgendaEmpregadoMapper;
import br.com.petsalus.mappers.EmpregadoMapper;
import br.com.petsalus.repositories.EmpresaEmpregadoRepository;
import br.com.petsalus.repositories.ServicoAgendaEmpregadoRepository;
import br.com.petsalus.utils.CustomExceptionUtils;
import br.com.petsalus.utils.EmpresaUtils;
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
    private final CustomExceptionUtils customExceptionUtils;

    private final RetornoService retornoService;

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
}