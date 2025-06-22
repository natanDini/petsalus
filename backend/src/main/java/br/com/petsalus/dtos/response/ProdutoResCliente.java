package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record ProdutoResCliente(
        Long id,
        String nome,
        String descricao,
        String preco,
        String fotoPerfil
) {
}
