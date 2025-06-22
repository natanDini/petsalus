package br.com.petsalus.dtos.request;

import java.math.BigDecimal;

public record ProdutoAdd (
        String nome,
        BigDecimal preco,
        boolean disponivel,
        BigDecimal qtdEstoque,
        String descricao
){
}