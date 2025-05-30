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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}