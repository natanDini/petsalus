package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CarrinhoRes(
    String precoTotalCarrinho,
    List<ProdutoRes> produtos
) {
}