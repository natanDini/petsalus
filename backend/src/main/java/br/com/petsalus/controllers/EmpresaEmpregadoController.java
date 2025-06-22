package br.com.petsalus.controllers;

import br.com.petsalus.dtos.response.AgendaEmpregado;
import br.com.petsalus.dtos.response.Empregado;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.EmpresaEmpregadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa-empregado")
@Tag(name = "EmpresaEmpregadoController", description = "Endpoints relacionados a requisições da relação Empresa Empregado")
public class EmpresaEmpregadoController {

    private final EmpresaEmpregadoService empresaEmpregadoService;

    @Operation(summary = "Relacionar Empregado a Empresa", description = "Este endpoint serve para relacionar Empregado a Empresa.")
    @PostMapping("/{empresaId}")
    public ResponseEntity<Retorno> relacionarEmpregadoEmpresa(@PathVariable Long empresaId, @RequestParam String cpf) throws CustomException {
        log.info(" >>> Tentando relacionar Empregado a Empresa.");
        return empresaEmpregadoService.relacionarEmpregadoEmpresa(empresaId, cpf);
    }

    @Operation(summary = "Empregados byEmpresaId", description = "Este endpoint serve para retornar empregados de uma empresa pelo id.")
    @GetMapping("/empregados/{empresaId}")
    public List<Empregado> empregadosByEmpresa(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar empregados de uma empresa pelo id.");
        return empresaEmpregadoService.empregadosByEmpresa(empresaId);
    }

    @Operation(summary = "Colaboradores byEmpresaId", description = "Este endpoint serve para retornar Colaboradores de uma empresa pelo id.")
    @GetMapping("/colaboradores/{empresaId}")
    public List<Empregado> colaboradoresByEmpresa(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar colaboradores de uma empresa pelo id.");
        return empresaEmpregadoService.colaboradoresByEmpresa(empresaId);
    }

    @Operation(summary = "Veterinários byEmpresaId", description = "Este endpoint serve para retornar Veterinários de uma empresa pelo id.")
    @GetMapping("/veterinarios/{empresaId}")
    public List<Empregado> veterinariosByEmpresa(@PathVariable Long empresaId) throws CustomException {
        log.info(" >>> Tentando retornar veterinarios de uma empresa pelo id.");
        return empresaEmpregadoService.veterinariosByEmpresa(empresaId);
    }

    @Operation(summary = "Agenda byEmpregado", description = "Este endpoint serve para retornar agenda de um empregado em uma data.")
    @GetMapping("/agenda")
    public List<AgendaEmpregado> agenda(@AuthenticationPrincipal Jwt jwt, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data) throws CustomException {
        log.info(" >>> Tentando retornar agenda de um empregado em uma data.");
        return empresaEmpregadoService.agenda(jwt, data);
    }

    @Operation(summary = "Vincular Empregado a Serviço", description = "Este endpoint serve para vincular empregado a um serviço.")
    @PostMapping("/servico/{empregadoId}/{servicoId}")
    public ResponseEntity<Retorno> vincularEmpregadoServico(@PathVariable Long empregadoId, @PathVariable Long servicoId) throws CustomException {
        log.info(" >>> Tentando vincular empregado a um serviço.");
        return empresaEmpregadoService.vincularEmpregadoServico(empregadoId, servicoId);
    }

    @Operation(summary = "Empregados byServicoId", description = "Este endpoint serve para retornar empregados relacionados a um servico pelo id.")
    @GetMapping("/empregados/servico/{servicoId}")
    public List<Empregado> empregadosByServico(@PathVariable Long servicoId) throws CustomException {
        log.info(" >>> Tentando retornar empregados relacionados a um servico pelo id..");
        return empresaEmpregadoService.empregadosByServico(servicoId);
    }

    @Operation(summary = "Retirar Empregado da Empresa", description = "Este endpoint serve para Retirar Empregado da Empresa.")
    @DeleteMapping("/deletar/{empresaId}/{empregadoId}")
    public ResponseEntity<Retorno> retirarEmpregadoEmpresa(@PathVariable Long empresaId, @PathVariable Long empregadoId) throws CustomException {
        log.info(" >>> Tentando Retirar Empregado da Empresa.");
        return empresaEmpregadoService.retirarEmpregadoEmpresa(empresaId, empregadoId);
    }

    @Operation(summary = "Retirar Empregado de Serviço", description = "Este endpoint serve para Retirar Empregado de Serviço.")
    @DeleteMapping("/servico/deletar/{servicoId}/{empregadoId}")
    public ResponseEntity<Retorno> retirarEmpregadoServico(@PathVariable Long servicoId, @PathVariable Long empregadoId) throws CustomException {
        log.info(" >>> Tentando Retirar  Empregado de Serviço.");
        return empresaEmpregadoService.retirarEmpregadoServico(servicoId, empregadoId);
    }
}