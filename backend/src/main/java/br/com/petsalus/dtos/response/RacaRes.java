package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record RacaRes(
        Long id,
        String nome
) {
}
