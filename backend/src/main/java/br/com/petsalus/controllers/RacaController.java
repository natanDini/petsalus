package br.com.petsalus.controllers;

import br.com.petsalus.dtos.request.RacaAdd;
import br.com.petsalus.dtos.response.RacaRes;
import br.com.petsalus.dtos.response.Retorno;
import br.com.petsalus.exceptions.CustomException;
import br.com.petsalus.services.RacaService;
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
@RequestMapping("/raca")
@Tag(name = "RacaController", description = "Endpoints relacionados a requisições de Raça")
public class RacaController {

    private final RacaService racaService;

    @Operation(summary = "Registrar Raça", description = "Este endpoint serve para registrar uma nova Raça.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestBody @Valid RacaAdd racaAdd) throws CustomException {
        log.info(" >>> Tentando registrar uma nova Raça.");
        return racaService.registrar(racaAdd);
    }

    @Operation(summary = "Raça getByEspecieId", description = "Este endpoint serve para retornar Raças de uma Espécie.")
    @GetMapping("/especie/{especieId}")
    public List<RacaRes> getByEspecieId(@PathVariable Long especieId) throws CustomException {
        log.info(" >>> Tentando retornar Raças de uma Espécie.");
        return racaService.getByEspecieId(especieId);
    }
}