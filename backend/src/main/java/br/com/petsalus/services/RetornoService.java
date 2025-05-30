package br.com.petsalus.services;

import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.enums.SeverityStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetornoService {

    private Retorno mapearRetorno(String message, SeverityStatus severity) {
        return Retorno.builder()
                .message(message)
                .severity(severity)
                .build();
    }

    public ResponseEntity<Retorno> retornoSucesso(String message) {
        Retorno retorno = mapearRetorno(message, SeverityStatus.SUCCESS);
        return ResponseEntity.ok().body(retorno);
    }

    public ResponseEntity<Retorno> retornoCustomizado(String message, SeverityStatus severity, HttpStatus status) {
        Retorno retorno = mapearRetorno(message, severity);
        return ResponseEntity.status(status).body(retorno);
    }
}
