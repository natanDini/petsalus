package br.com.petsalus.utils;

import br.com.petsalus.entities.Endereco;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnderecoUtils {

    private final EnderecoRepository enderecoRepository;

    public Endereco findById(Long id){
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereço informado não encontrado."));
    }
}