package br.com.petsalus.utils;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmpresaUtils {

    private final EmpresaRepository empresaRepository;

    public Empresa findById(Long id){
        return empresaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empresa informada não encontrada."));
    }
}