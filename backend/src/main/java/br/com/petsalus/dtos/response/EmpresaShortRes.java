package br.com.petsalus.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record EmpresaShortRes(

    @JsonFormat(pattern = "HH:mm")
    LocalTime horaAbertura,

    @JsonFormat(pattern = "HH:mm")
    LocalTime horaEncerramento,

    Long id,
    String nome,
    String descricao,
    String modeloComercial,
    boolean trabalhaVinteQuatroHoras,
    String fotoPerfil) {
}