package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutosComprasEmpresa (
        Long id,
        BigDecimal qtd,
        String valorProduto,
        String valorTotalProduto,
        String nomeProduto,
        String fotoProduto
){
}
