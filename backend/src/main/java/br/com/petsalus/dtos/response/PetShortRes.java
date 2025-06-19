package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record PetShortRes(

        Long id,
        String nome,
        String fotoPerfil) {
}
