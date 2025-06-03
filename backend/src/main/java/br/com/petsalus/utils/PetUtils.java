package br.com.petsalus.utils;

import br.com.petsalus.entities.Pet;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PetUtils {

    private final PetRepository petRepository;

    public Pet findById(Long id){
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet informado não encontrado."));
    }
}