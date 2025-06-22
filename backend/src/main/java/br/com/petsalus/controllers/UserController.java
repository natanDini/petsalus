package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.UserAdd;
import br.com.petsalus.dtos.request.UserEdit;
import br.com.petsalus.dtos.response.PerfilCompleto;
import br.com.petsalus.dtos.response.PerfilPequeno;
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
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid UserAdd userAdd) throws CustomException {
        log.info(" >>> Tentando registrar um novo User.");
        return userService.registrar(userAdd);
    }

    @Operation(summary = "Registrar Empregado", description = "Este endpoint serve para registrar um novo Empregado.")
    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<Retorno> registrarEmpregado(@PathVariable Long empresaId, @RequestBody @Valid UserAdd userAdd) throws CustomException {
        log.info(" >>> Tentando registrar um novo Empregado.");
        return userService.registrarEmpregado(empresaId,userAdd );
    }

    @Operation(summary = "Upload Foto", description = "Este endpoint serve para alterar foto de usuário logado.")
    @PostMapping("/upload-foto")
    public String uploadFoto(@RequestParam MultipartFile foto, @AuthenticationPrincipal Jwt jwt)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando alterar sua foto de perfil na aplicação.");
        return userService.uploadFoto(foto, jwt);
    }

    @Operation(summary = "Perfil - Pequeno", description = "Este endpoint serve para retornar perfil pequeno de usuário.")
    @GetMapping("/perfil-pequeno")
    public PerfilPequeno perfilPequeno(@AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando retornar perfil pequeno de um usuário.");
        return userService.perfilPequeno(jwt);
    }

    @Operation(summary = "Perfil - Completo", description = "Este endpoint serve para retornar perfil completo de usuário.")
    @GetMapping("/perfil-completo")
    public PerfilCompleto perfilCompleto(@AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando retornar perfil completo de um usuário.");
        return userService.perfilCompleto(jwt);
    }

    @Operation(summary = "Deletar User", description = "Este endpoint serve para deletar um User/Conta.")
    @DeleteMapping("/deletar")
    public ResponseEntity<Retorno> deletar(@AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando deletar uma Empresa.");
        return userService.deletar(jwt);
    }

    @Operation(summary = "Editar User", description = "Este endpoint serve para editar um User/Conta.")
    @PutMapping("/editar")
    public ResponseEntity<Retorno> editar(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid UserEdit userEdit) throws CustomException {
        log.info(" >>> Tentando editar uma Empresa.");
        return userService.editar(jwt, userEdit);
    }
}