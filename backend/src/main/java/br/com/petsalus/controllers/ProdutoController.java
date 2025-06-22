package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.ProdutoAdd;
import br.com.petsalus.dtos.response.ProdutoResCliente;
import br.com.petsalus.dtos.response.ProdutoResEmpresa;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
@Tag(name = "ProdutoController", description = "Endpoints relacionados a requisições de Produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "Registrar Produto", description = "Este endpoint serve para registrar um novo Produto.")
    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<Retorno> registrar(@PathVariable Long empresaId, @RequestBody ProdutoAdd produtoAdd) throws CustomException {
        log.info(" >>> Tentando registrar um novo Produto.");
        return produtoService.registrar(empresaId, produtoAdd);
    }

    @Operation(summary = "Produtos byEmpresa pra Cliente", description = "Este endpoint serve para retornar Produtos de uma empresa para um cliente.")
    @GetMapping("/empresa/{empresaId}/cliente")
    public List<ProdutoResCliente> getByEmpresaIdClient(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar Produtos de uma empresa para um cliente.");
        return produtoService.getByEmpresaIdClient(empresaId);
    }

    @Operation(summary = "Produtos byEmpresa", description = "Este endpoint serve para retornar Produtos de uma empresa.")
    @GetMapping("/empresa/{empresaId}")
    public List<ProdutoResEmpresa> getByEmpresaId(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar Produtos de uma empresa.");
        return produtoService.getByEmpresaId(empresaId);
    }

    @Operation(summary = "Adicionar Estoque produtoById", description = "Este endpoint serve para adicionar estoque de um produto pelo id.")
    @PatchMapping("/add-estoque/{produtoId}")
    public ResponseEntity<Retorno> addEstoque(@PathVariable Long produtoId, @RequestParam Long qtdAddEstoque) throws CustomException {
        log.info(" >>> Tentando adicionar estoque de um produto pelo id.");
        return produtoService.addEstoque(produtoId, qtdAddEstoque);
    }
}