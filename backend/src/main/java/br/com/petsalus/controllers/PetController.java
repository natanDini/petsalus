package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.repositories.PetRepository;
import br.com.petsalus.services.PetService;
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
@RequestMapping("/pet")
@Tag(name = "PetController", description = "Endpoints relacionados a requisições de Pet")
public class PetController {

    private final PetService petService;

    @Operation(summary = "Registrar Pet", description = "Este endpoint serve para registrar um novo Pet.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid PetAdd petAdd) throws CustomException {
        log.info(" >>> Tentando registrar um novo Pet.");
        return petService.registrar(petAdd);
    }
}