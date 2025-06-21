package br.com.petsalus.utils;

import br.com.petsalus.entities.ServicoAgenda;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.ServicoAgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServicoAgendaUtils {

    private final ServicoAgendaRepository servicoAgendaRepository;

    public ServicoAgenda findById(Long id){
        return servicoAgendaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço Agendado informado não encontrado."));
    }
}