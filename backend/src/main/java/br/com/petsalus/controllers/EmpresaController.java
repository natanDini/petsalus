package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.EmpresaAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa")
@Tag(name = "EmpresaController", description = "Endpoints relacionados a requisições de Empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Operation(summary = "Registrar Empresa", description = "Este endpoint serve para registrar uma nova Empresa.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid EmpresaAdd empresaAdd, @AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando registrar uma nova Empresa.");
        return empresaService.registrar(empresaAdd, jwt);
    }
}