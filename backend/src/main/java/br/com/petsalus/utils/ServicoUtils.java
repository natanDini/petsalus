package br.com.petsalus.utils;

import br.com.petsalus.entities.Servico;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.ServicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServicoUtils {

    private final ServicoRepository servicoRepository;

    public Servico findById(Long id){
        return servicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço informado não encontrado."));
    }
}