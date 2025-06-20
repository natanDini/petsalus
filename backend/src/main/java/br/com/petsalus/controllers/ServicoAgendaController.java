package br.com.petsalus.controllers;

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

    @Operation(summary = "Registrar ServiçoAgenda", description = "Este endpoint serve para registrar um novo ServiçoAgenda.")
    @PostMapping("/registrar/{servicoId}/{petId}")
    public ResponseEntity<Retorno> registrar(@PathVariable Long servicoId, @PathVariable Long petId, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime inicioSolicitado) throws CustomException {
        log.info(" >>> Tentando registrar um novo ServiçoAgenda.");
        return servicoAgendaService.registrar(servicoId, petId, inicioSolicitado);
    }

    @Operation(summary = "Horários disponíveis", description = "Retorna todos os horários livres para o serviço na data informada")
    @GetMapping("/disponiveis/{servicoId}")
    public ResponseEntity<List<String>> buscarHorariosDisponiveis(
            @PathVariable Long servicoId,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data)
            throws CustomException {

        log.info(">>> Buscando horários disponíveis para serviço {} em {}", servicoId, data);
        return servicoAgendaService.buscarHorariosDisponiveis(servicoId, data);
    }
}