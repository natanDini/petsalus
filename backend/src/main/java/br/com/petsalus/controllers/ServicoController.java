package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.ServicoAdd;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.dtos.response.ServicoRes;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/servico")
@Tag(name = "ServicoController", description = "Endpoints relacionados a requisições de Serviços")
public class ServicoController {

    private final ServicoService servicoService;

    @Operation(summary = "Registrar Servico", description = "Este endpoint serve para registrar um Serviço de uma Empresa.")
    @PostMapping("/{empresaId}/registrar")
    public ResponseEntity<Retorno> registrar(@PathVariable Long empresaId, @RequestBody @Valid ServicoAdd servicoAdd) throws CustomException {
        log.info(" >>> Tentando registrar um novo servico.");
        return servicoService.registrar(empresaId, servicoAdd);
    }

    @Operation(summary = "Serviços byEmpresaId", description = "Este endpoint serve para retornar Serviços em uma Empresa.")
    @GetMapping("/{empresaId}")
    public List<ServicoRes> getServicosByEmpresaId(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar Serviços de uma empresa.");
        return servicoService.getServicosByEmpresaId(empresaId);
    }

    @Operation(summary = "Serviços byEmpresaId Veterinários", description = "Este endpoint serve para retornar Serviços Veterinários em uma Empresa.")
    @GetMapping("/veterinarios/{empresaId}")
    public List<ServicoRes> getServicosVeterinariosByEmpresaId(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar Serviços Veterinários de uma empresa.");
        return servicoService.getServicosVeterinariosByEmpresaId(empresaId);
    }

    @Operation(summary = "Serviços Públicos byEmpresaId", description = "Este endpoint serve para retornar Serviços que usuários podem marcar sozinhos em uma Empresa.")
    @GetMapping("/{empresaId}/publicos")
    public List<ServicoRes> getServicosPublicosByEmpresaId(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar Serviços Públicos de uma empresa.");
        return servicoService.getServicosPublicosByEmpresaId(empresaId);
    }

    @Operation(summary = "Deletar Serviço", description = "Este endpoint serve para deletar um Serviço.")
    @DeleteMapping("/deletar/{servicoId}")
    public ResponseEntity<Retorno> deletar(@PathVariable Long servicoId) throws CustomException {
        log.info(" >>> Tentando deletar um Serviço.");
        return servicoService.deletar(servicoId);
    }

    @Operation(summary = "Editar Servico", description = "Este endpoint serve para editar um Serviço de uma Empresa.")
    @PutMapping("/editar/{servicoId}")
    public ResponseEntity<Retorno> editar(@PathVariable Long servicoId, @RequestBody @Valid ServicoAdd servicoAdd) throws CustomException {
        log.info(" >>> Tentando editar um servico.");
        return servicoService.editar(servicoId, servicoAdd);
    }
}