package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.CompraAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compra")
@Tag(name = "CompraController", description = "Endpoints relacionados a requisições de Compras")
public class CompraController {

    private final CompraService compraService;

    @Operation(summary = "Comprar meu Carrinho", description = "Este endpoint serve para comprar meu Carrinho.")
    @PostMapping("/comprar")
    public ResponseEntity<Retorno> comprar(@AuthenticationPrincipal Jwt jwt, @RequestBody CompraAdd compraAdd) throws CustomException {
        log.info(" >>> Tentando comprar meu carrinho.");
        return compraService.comprar(jwt, compraAdd);
    }
}