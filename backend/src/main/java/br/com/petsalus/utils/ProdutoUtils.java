package br.com.petsalus.utils;

import br.com.petsalus.entities.Produto;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProdutoUtils {

    private final ProdutoRepository produtoRepository;

    public Produto findById(Long id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto informado não encontrado."));
    }
}