package br.com.petsalus.utils;

import br.com.petsalus.entities.Especie;
import br.com.petsalus.entities.Raca;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.RacaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RacaUtils {

    private final RacaRepository racaRepository;

    public Raca findById(Long id){
        return racaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Raça informada não encontrada."));
    }
}