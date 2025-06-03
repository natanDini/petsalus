package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.UserService;
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
@RequestMapping("/user")
@Tag(name = "UserController", description = "Endpoints relacionados a requisições de User")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Registrar User", description = "Este endpoint serve para registrar um novo User.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid UserAdd alunoAdd) throws CustomException {
        log.info(" >>> Tentando registrar um novo User.");
        return userService.registrar(alunoAdd);
    }

    @Operation(summary = "Upload Foto", description = "Este endpoint serve para alterar foto de usuário logado.")
    @PostMapping("/upload-foto")
    public ResponseEntity<Retorno> uploadFoto(@RequestParam MultipartFile foto, @AuthenticationPrincipal Jwt jwt)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando alterar sua foto de perfil na aplicação");
        return userService.uploadFoto(foto, jwt);
    }
}