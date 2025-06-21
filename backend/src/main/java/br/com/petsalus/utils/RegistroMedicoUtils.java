package br.com.petsalus.utils;

import br.com.petsalus.entities.Pet;
import br.com.petsalus.entities.RegistroMedico;
import br.com.petsalus.entities.ServicoAgenda;
import br.com.petsalus.enums.RegistroMedicoStatus;
import br.com.petsalus.repositories.RegistroMedicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistroMedicoUtils {

    private final RegistroMedicoRepository registroMedicoRepository;

    public void salvar(Pet pet, ServicoAgenda servicoAgenda, String descricao) {

        RegistroMedico registroMedico = new RegistroMedico();

        registroMedico.setPet(pet);
        registroMedico.setDescricao(descricao);
        registroMedico.setServicoAgenda(servicoAgenda);
        registroMedico.setStatus(RegistroMedicoStatus.ROTINA_AGENDAMENTO);

        registroMedicoRepository.save(registroMedico);
    }
}