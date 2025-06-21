package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record PerfilPequeno (

        Long id,
        String nome,
        String fotoPerfil){
}