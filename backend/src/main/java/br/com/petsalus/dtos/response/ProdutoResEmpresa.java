package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoResEmpresa(
        Long id,
        String nome,
        String descricao,
        String preco,
        boolean disponivel,
        BigDecimal qtdEstoque,
        BigDecimal qtdVendida,
        String fotoPerfil
) {
}