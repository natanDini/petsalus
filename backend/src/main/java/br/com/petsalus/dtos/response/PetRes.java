package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record PetRes(
        
     Long id,
     Float peso,
     String nome,
     Integer idade,
     String sexo,
     String raca,
     String especie,
     String fotoPerfil) {
}