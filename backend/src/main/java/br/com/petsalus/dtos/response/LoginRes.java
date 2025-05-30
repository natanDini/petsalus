package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record LoginRes(
        String token, String userRole) {
}