package br.com.petsalus.controllers;

import br.com.petsalus.dtos.response.CarrinhoRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carrinho")
@Tag(name = "CarrinhoController", description = "Endpoints relacionados a requisições de Carrinho.")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @Operation(summary = "Registrar Produto no Carrinho", description = "Este endpoint serve para registrar um novo Produto no Carrinh.")
    @PostMapping("/registrar/{produtoId}")
    public ResponseEntity<Retorno> registrar(@PathVariable Long produtoId, @RequestParam Long quantidade, @AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando registrar um novo Produto no Carrinho.");
        return carrinhoService.registrar(produtoId, quantidade, jwt);
    }

    @Operation(summary = "Atualizar qtd Produto no Carrinho", description = "Este endpoint serve para atualizar a qtd de um Produto no Carrinh.")
    @PatchMapping("/atualizar/{produtoCarrinhoId}/{qtd}")
    public ResponseEntity<Retorno> atualizar(@PathVariable Long produtoCarrinhoId, @PathVariable Long qtd) throws CustomException {
        log.info(" >>> Tentando atualizar qtd de um Produto no Carrinho.");
        return carrinhoService.atualizar(produtoCarrinhoId, qtd);
    }

    @Operation(summary = "Meu Carrinho", description = "Este endpoint serve para mostrar o Carrinho do Usuário.")
    @GetMapping("/meu-carrinho")
    public CarrinhoRes meuCarrinho(@AuthenticationPrincipal Jwt jwt) {
        log.info(" >>> Tentando mostrar o Carrinho do Usuário.");
        return carrinhoService.meuCarrinho(jwt);
    }

    @Operation(summary = "Deletar Produto no Carrinho", description = "Este endpoint serve para deletar um Produto no Carrinho.")
    @DeleteMapping("/deletar/{produtoCarrinhoId}")
    public ResponseEntity<Retorno> deletarProdutoCarrinho(@PathVariable Long produtoCarrinhoId) throws CustomException {
        log.info(" >>> Tentando deletar um Produto no Carrinho.");
        return carrinhoService.deletarProdutoCarrinho(produtoCarrinhoId);
    }

    @Operation(summary = "Deletar Carrinho", description = "Este endpoint serve para deletar um Carrinho.")
    @DeleteMapping("/deletar")
    public ResponseEntity<Retorno> deletar(@AuthenticationPrincipal Jwt jwt) throws CustomException {
        log.info(" >>> Tentando deletar um Carrinho.");
        return carrinhoService.deletar(jwt);
    }
}