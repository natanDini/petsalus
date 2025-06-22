package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoRes(
        String nome,
        String preco,
        String empresa,
        BigDecimal quantidade,
        String precoTotalProduto,
        String fotoPerfil
) {
}