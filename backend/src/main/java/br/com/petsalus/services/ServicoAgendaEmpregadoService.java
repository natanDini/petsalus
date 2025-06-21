package br.com.petsalus.services;

import br.com.petsalus.entities.ServicoAgenda;
import br.com.petsalus.entities.ServicoAgendaEmpregado;
import br.com.petsalus.entities.User;
import br.com.petsalus.repositories.ServicoAgendaEmpregadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicoAgendaEmpregadoService {

    private final ServicoAgendaEmpregadoRepository servicoAgendaEmpregadoRepository;

    public void registrarEmergencia(User veterinario, ServicoAgenda servicoAgenda){

        ServicoAgendaEmpregado servicoAgendaEmpregado = new ServicoAgendaEmpregado();

        servicoAgendaEmpregado.setEmpregado(veterinario);
        servicoAgendaEmpregado.setServicoAgenda(servicoAgenda);

        servicoAgendaEmpregadoRepository.save(servicoAgendaEmpregado);
    }
}