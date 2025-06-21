package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.RegistroMedicoAdd;
import br.com.petsalus.dtos.response.RegistroMedicoRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.RegistroMedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registro-medico")
@Tag(name = "RegistroMedicoController", description = "Endpoints relacionados a requisições de Registro Médico")
public class RegistroMedicoController {

    private final RegistroMedicoService registroMedicoService;

    @Operation(summary = "Registrar Registro Médico", description = "Este endpoint serve para registrar um novo Registro Médico.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid RegistroMedicoAdd registroMedicoAdd, @AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando registrar um novo Registro Médico.");
        return registroMedicoService.registrar(registroMedicoAdd, jwt);
    }

    @Operation(summary = "Registro Médico byPet", description = "Este endpoint serve para retornar o Registro Médico de um Pet.")
    @GetMapping("/pet/{petId}")
    public List<RegistroMedicoRes> resgistroByPet(@PathVariable Long petId) throws CustomException {
        log.info(" >>> Tentando retornar RegistroMédico byPet.");
        return registroMedicoService.resgistroByPet(petId);
    }
}