package br.com.petsalus.dtos.request;

import java.math.BigDecimal;

public record ProdutoEdit(
        String nome,
        BigDecimal preco,
        boolean disponivel,
        String descricao
) {
}
