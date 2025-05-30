package br.com.petsalus.dtos.response;

import br.com.petsalus.enums.SeverityStatus;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record RetornoException(
        String message, SeverityStatus severity, OffsetDateTime timestamp, String path) {
}
