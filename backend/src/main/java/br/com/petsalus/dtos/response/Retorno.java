package br.com.petsalus.dtos.response;

import br.com.petsalus.enums.SeverityStatus;
import lombok.Builder;

@Builder
public record Retorno(

        String message, SeverityStatus severity) {
}