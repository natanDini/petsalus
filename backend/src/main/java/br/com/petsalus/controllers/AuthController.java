package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.Login;
import br.com.petsalus.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Endpoints relacionados a requisições de Auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Este endpoint serve para realizar login na aplicação.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        log.info(" >>> User de username {} está tentando realizar login.", login.username());
        return authService.login(login);
    }
}