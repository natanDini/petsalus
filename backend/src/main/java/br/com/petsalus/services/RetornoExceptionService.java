package br.com.petsalus.services;

import br.com.petsalus.dtos.response.RetornoException;
import br.com.petsalus.enums.SeverityStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetornoExceptionService {

    public ResponseEntity<RetornoException> buildInternalServerErrorResponse(String path) {
        RetornoException retornoException = RetornoException.builder()
                .message("Erro inesperado durante o processamento.")
                .severity(SeverityStatus.ERROR)
                .timestamp(OffsetDateTime.now())
                .path(path)
                .build();

        return ResponseEntity.internalServerError().body(retornoException);
    }

    public ResponseEntity<RetornoException> buildBadRequestResponse(String message, String path) {
        RetornoException retornoException = RetornoException.builder()
                .message(message)
                .severity(SeverityStatus.ERROR)
                .timestamp(OffsetDateTime.now())
                .path(path)
                .build();

        return ResponseEntity.badRequest().body(retornoException);
    }

    public ResponseEntity<RetornoException> buildResponse(String message, SeverityStatus severity,
                                                           HttpStatus status, String path) {
        RetornoException retornoException = RetornoException.builder()
                .message(message)
                .severity(severity)
                .timestamp(OffsetDateTime.now())
                .path(path)
                .build();

        return ResponseEntity.status(status).body(retornoException);
    }
}