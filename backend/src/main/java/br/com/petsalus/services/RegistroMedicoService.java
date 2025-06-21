package br.com.petsalus.services;

import br.com.petsalus.dtos.request.RegistroMedicoAdd;
import br.com.petsalus.dtos.response.RegistroMedicoRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.entities.*;
import br.com.petsalus.enums.RegistroMedicoStatus;
import br.com.petsalus.enums.UserRole;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.mappers.RegistroMedicoResMapper;
import br.com.petsalus.repositories.RegistroMedicoRepository;
import br.com.petsalus.utils.CustomExceptionUtils;
import br.com.petsalus.utils.PetUtils;
import br.com.petsalus.utils.ServicoUtils;
import br.com.petsalus.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroMedicoService {

    private final PetUtils petUtils;
    private final UserUtils userUtils;
    private final ServicoUtils servicoUtils;
    private final CustomExceptionUtils customExceptionUtils;

    private final RetornoService retornoService;
    private final ServicoAgendaService servicoAgendaService;
    private final ServicoAgendaEmpregadoService servicoAgendaEmpregadoService;

    private final RegistroMedicoRepository registroMedicoRepository;

    public ResponseEntity<Retorno> registrar(RegistroMedicoAdd registroMedicoAdd, Jwt jwt) throws CustomException {

        User veterinario = userUtils.findByJwt(jwt);

        if (!veterinario.getUserRole().equals(UserRole.VETERINARIO)){
            throw customExceptionUtils.errorAndBadRequest("Apenas veterinários podem fazer registro médico de emergência.");
        }

        Pet pet = petUtils.findById(registroMedicoAdd.petId());
        Servico servico = servicoUtils.findById(registroMedicoAdd.servicoId());

        ServicoAgenda servicoAgenda = servicoAgendaService.registrarEmergencia(pet, servico, registroMedicoAdd.dataHora(), veterinario);

        servicoAgendaEmpregadoService.registrarEmergencia(veterinario, servicoAgenda);

        RegistroMedico registroMedico = new RegistroMedico();

        registroMedico.setPet(pet);
        registroMedico.setServicoAgenda(servicoAgenda);
        registroMedico.setStatus(RegistroMedicoStatus.EMERGENCIA);
        registroMedico.setDescricao(registroMedicoAdd.descricao());

        registroMedicoRepository.save(registroMedico);

        log.info(" >>> Registro Médico cadastrado com sucesso.");
        return retornoService.retornoSucesso("Registro Médico cadastrado com sucesso.");
    }

    public List<RegistroMedicoRes> resgistroByPet(Long petId) throws CustomException {

        Pet pet = petUtils.findById(petId);

        List<RegistroMedico> registros = registroMedicoRepository.findByPetOrderByServicoAgenda_DataHoraDesc(pet);

        return RegistroMedicoResMapper.map(registros);
    }
}