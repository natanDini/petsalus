package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.EspecieAdd;
import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.EspecieService;
import br.com.petsalus.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/especie")
@Tag(name = "EspecieController", description = "Endpoints relacionados a requisições de Espécie")
public class EspecieController {

    private final EspecieService especieService;

    @Operation(summary = "Registrar Espécie", description = "Este endpoint serve para registrar uma nova Espécie.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid EspecieAdd especieAdd) throws CustomException {
        log.info(" >>> Tentando registrar uma nova Espécie.");
        return especieService.registrar(especieAdd);
    }
}