package br.com.petsalus.dtos.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MeusAgendamentos (

        List<Agendamento> pendentes,
        List<Agendamento> cancelados,
        List<Agendamento> efetivados
        ){
}