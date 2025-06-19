package br.com.petsalus.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record EmpresaShortRes(
       
         Long id,
         String nome,
         String descricao,
         String fotoPerfil,
         String modeloComercial,
         boolean trabalhaVinteQuatroHoras,

         @JsonFormat(pattern = "HH:mm")
         LocalTime horaAbertura,

         @JsonFormat(pattern = "HH:mm")
         LocalTime horaEncerramento) {
}