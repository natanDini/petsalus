package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.ProdutoAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}