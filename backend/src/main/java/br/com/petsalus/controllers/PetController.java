package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
@Tag(name = "PetController", description = "Endpoints relacionados a requisições de Pet")
public class PetController {

    private final PetService petService;

    @Operation(summary = "Registrar Pet", description = "Este endpoint serve para registrar um novo Pet.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid PetAdd petAdd, @AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando registrar um novo Pet.");
        return petService.resgistrar(petAdd, jwt);
    }

    @Operation(summary = "Upload Foto", description = "Este endpoint serve para alterar foto de pet.")
    @PostMapping("/upload-foto/{petId}")
    public ResponseEntity<Retorno> uploadFoto(@PathVariable Long petId, @RequestParam MultipartFile foto)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando alterar a foto de seu pet.");
        return petService.uploadFoto(petId, foto);
    }
}