package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record Agendamento (

        String dataHoraAgendada,
        String empresa,
        String servico,
        String preco,
        Long tempoServicoMin){
}