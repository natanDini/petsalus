package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.ProdutoAdd;
import br.com.petsalus.dtos.request.ProdutoEdit;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Operation(summary = "Upload Foto", description = "Este endpoint serve para alterar foto de produto.")
    @PostMapping("/upload-foto/{produtoId}")
    public String uploadFoto(@PathVariable Long produtoId, @RequestParam MultipartFile foto)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando alterar a foto de seu produto.");
        return produtoService.uploadFoto(produtoId, foto);
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

    @Operation(summary = "Indisponível produtoById", description = "Este endpoint serve para indisponibilizar um produto pelo id.")
    @PatchMapping("/indisponibilizar/{produtoId}")
    public ResponseEntity<Retorno> indisponibilizar(@PathVariable Long produtoId) throws CustomException {
        log.info(" >>> Tentando indisponibilizar um produto pelo id.");
        return produtoService.indisponibilizar(produtoId);
    }

    @Operation(summary = "Disponível produtoById", description = "Este endpoint serve para disponibilizar um produto pelo id.")
    @PatchMapping("/disponibilizar/{produtoId}")
    public ResponseEntity<Retorno> disponibilizar(@PathVariable Long produtoId) throws CustomException {
        log.info(" >>> Tentando disponibilizar um produto pelo id.");
        return produtoService.disponibilizar(produtoId);
    }

    @Operation(summary = "Deletar Produto", description = "Este endpoint serve para deletar um Produto.")
    @DeleteMapping("/deletar/{produtoId}")
    public ResponseEntity<Retorno> deletar(@PathVariable Long produtoId) throws CustomException {
        log.info(" >>> Tentando deletar um Produto.");
        return produtoService.deletar(produtoId);
    }

    @Operation(summary = "Editar Produto", description = "Este endpoint serve para editar um Produto.")
    @PutMapping("/editar/{produtoId}")
    public ResponseEntity<Retorno> editar(@PathVariable Long produtoId, @RequestBody ProdutoEdit produtoEdit) throws CustomException {
        log.info(" >>> Tentando editar um novo Produto.");
        return produtoService.editar(produtoId, produtoEdit);
    }
}