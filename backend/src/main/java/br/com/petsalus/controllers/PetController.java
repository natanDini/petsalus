package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.PetAdd;
import br.com.petsalus.dtos.response.PetRes;
import br.com.petsalus.dtos.response.PetShortRes;
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
import java.util.List;

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
        return petService.registrar(petAdd, jwt);
    }

    @Operation(summary = "Upload Foto", description = "Este endpoint serve para alterar foto de pet.")
    @PostMapping("/upload-foto/{petId}")
    public ResponseEntity<Retorno> uploadFoto(@PathVariable Long petId, @RequestParam MultipartFile foto)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando alterar a foto de seu pet.");
        return petService.uploadFoto(petId, foto);
    }

    @Operation(summary = "Pet byId", description = "Este endpoint serve para retornar um pet pelo Id.")
    @GetMapping("/{petId}")
    public PetRes getById(@PathVariable Long petId) throws CustomException {
        log.info(" >>> Tentando retornar um pet pelo Id.");
        return petService.getById(petId);
    }

    @Operation(summary = "My Pets", description = "Este endpoint serve para retornar pets de um tutor logado.")
    @GetMapping
    public List<PetShortRes> myPets(@AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando retornar pets de um tutor logado.");
        return petService.myPets(jwt);
    }

    @Operation(summary = "Pets by CPF tutor", description = "Este endpoint serve para retornar pets pelo cpf de um tutor.")
    @GetMapping("/cpf/{cpf}")
    public List<PetShortRes> petsByTutor(@PathVariable String cpf) throws CustomException {
        log.info(" >>> Tentando retornar pets de um tutor.");
        return petService.petsByTutor(cpf);
    }

    @Operation(summary = "Deletar Pet", description = "Este endpoint serve para deletar um Pet.")
    @DeleteMapping("/deletar/{petId}")
    public ResponseEntity<Retorno> deletar(@PathVariable Long petId) throws CustomException {
        log.info(" >>> Tentando deletar um Pet.");
        return petService.deletar(petId);
    }

    @Operation(summary = "Editar Pet", description = "Este endpoint serve para editar um Pet.")
    @PutMapping("/editar/{petId}")
    public ResponseEntity<Retorno> editar(@PathVariable Long petId, @RequestBody @Valid PetAdd petAdd) throws CustomException {
        log.info(" >>> Tentando editar um Pet.");
        return petService.editar(petId, petAdd);
    }
}