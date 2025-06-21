package br.com.petsalus.controllers;

import br.com.petsalus.dtos.response.MeusAgendamentos;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.ServicoAgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/servico_agenda")
@Tag(name = "ServicoAgendaController", description = "Endpoints relacionados a requisições de Serviços Agenda")
public class ServicoAgendaController {

    private final ServicoAgendaService servicoAgendaService;

    @Operation(summary = "Horários disponíveis", description = "Retorna todos os horários livres para o serviço na data informada")
    @GetMapping("/disponiveis/{servicoId}")
    public ResponseEntity<List<String>> buscarHorariosDisponiveis(
            @PathVariable Long servicoId,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data)
            throws CustomException {

        log.info(" >>> Buscando horários disponíveis para serviço {} em {}", servicoId, data);
        return servicoAgendaService.buscarHorariosDisponiveis(servicoId, data);
    }

    @Operation(summary = "Agendar Serviço", description = "Este endpoint serve para registrar um novo ServiçoAgenda.")
    @PostMapping("/registrar/{servicoId}/{petId}")
    public ResponseEntity<Retorno> registrar(@PathVariable Long servicoId, @PathVariable Long petId, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime inicioSolicitado) throws CustomException {
        log.info(" >>> Tentando registrar um novo ServiçoAgenda.");
        return servicoAgendaService.registrar(servicoId, petId, inicioSolicitado);
    }

    @Operation(summary = "Cancelar Serviço Agendado", description = "Este endpoint serve para cancelar um Serviço Agendado pendente.")
    @PatchMapping("/cancelar/{servicoAgendaId}")
    public ResponseEntity<Retorno> cancelar(@PathVariable Long servicoAgendaId) throws CustomException {
        log.info(" >>> Tentando cancelar um Serviço Agendado pendente.");
        return servicoAgendaService.cancelar(servicoAgendaId);
    }

    @Operation(summary = "Efetivar Serviço Agendado", description = "Este endpoint serve para efetivar um Serviço Agendado pendente.")
    @PatchMapping("/efetivar/{servicoAgendaId}")
    public ResponseEntity<Retorno> efetivar(@PathVariable Long servicoAgendaId) throws CustomException {
        log.info(" >>> Tentando efetivar um Serviço Agendado pendente.");
        return servicoAgendaService.efetivar(servicoAgendaId);
    }

    @Operation(summary = "Relatório de Serviços Agendados byPet", description = "Este endpoint serve para retornar um lista de Serviços CANCELADOS, PENDENTES e EFETIVADOS de um pet pelo Id.")
    @GetMapping("/meus-agendamentos/{petId}")
    public MeusAgendamentos meusAgendamentos(@PathVariable Long petId) throws CustomException {
        log.info(" >>> Tentando retornar lista de Agendamentos de Pet.");
        return servicoAgendaService.meusAgendamentos(petId);
    }
}