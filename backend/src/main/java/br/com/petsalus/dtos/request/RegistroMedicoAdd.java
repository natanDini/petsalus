package br.com.petsalus.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RegistroMedicoAdd (
        Long petId,
        Long servicoId,
        String descricao,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dataHora
        ){
}
