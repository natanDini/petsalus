package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Agendamento (

        LocalDateTime dataHoraAgendada,
        String empresa,
        String servico,
        String preco,
        Long tempoServicoMin){
}