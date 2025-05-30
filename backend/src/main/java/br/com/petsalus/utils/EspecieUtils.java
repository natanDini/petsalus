package br.com.petsalus.utils;

import br.com.petsalus.entities.Especie;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.EspecieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EspecieUtils {

    private final EspecieRepository especieRepository;

    public Especie findById(Long id){
        return especieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Espécie informada não encontrada."));
    }
}