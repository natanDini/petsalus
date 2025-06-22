package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MeusProdutosCompras (
        Long id,
        BigDecimal qtd,
        String valorProduto,
        String valorTotalProduto,
        String nomeProduto,
        String empresa,
        String fotoProduto
){
}
