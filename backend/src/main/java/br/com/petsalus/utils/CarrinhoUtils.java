package br.com.petsalus.utils;

import br.com.petsalus.entities.Carrinho;
import br.com.petsalus.entities.Produto;
import br.com.petsalus.entities.User;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.CarrinhoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarrinhoUtils {

    private final CarrinhoRepository carrinhoRepository;

    public Carrinho findById(Long id){
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carrinho informado não encontrado."));
    }

    public Carrinho findByTutorAndProduto(User tutor, Produto produto){
        return carrinhoRepository.findByTutorAndProduto(tutor, produto)
                .orElseThrow(() -> new NotFoundException("Carrinho informado não encontrado."));
    }
}