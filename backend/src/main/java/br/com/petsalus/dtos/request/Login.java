package br.com.petsalus.dtos.request;

import lombok.Builder;

@Builder
public record Login(
        String senha, String username) {
}