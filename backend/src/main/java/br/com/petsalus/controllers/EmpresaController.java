package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.EmpresaAdd;
import br.com.petsalus.dtos.response.EmpresaRes;
import br.com.petsalus.dtos.response.EmpresaShortRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
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

    @Operation(summary = "Empresa getAll", description = "Este endpoint serve para listar todas as Empresas.")
    @GetMapping
    public List<EmpresaShortRes> listar() throws CustomException {
        log.info(" >>> Tentando listar todas as Empresas.");
        return empresaService.listar();
    }

    @Operation(summary = "Empresa byId", description = "Este endpoint serve para retornar uma empresa pelo Id.")
    @GetMapping("/{empresaId}")
    public EmpresaRes getById(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar uma empresa pelo Id.");
        return empresaService.getById(empresaId);
    }

//    @Operation(summary = "Empresas 24h", description = "Este endpoint serve para retornar empresas (CLINICA, CLINICA_E_PET_SHOP) que funcionam 24h.")
//    @GetMapping("/clinicas/emergencias")
//    public ResponseEntity<Retorno> emergencias(@RequestBody @Valid EmpresaAdd empresaAdd, @AuthenticationPrincipal Jwt jwt) throws CustomException {
//        log.info(" >>> Tentando registrar uma nova Empresa.");
//        return empresaService.registrar(empresaAdd, jwt);
//    }
}