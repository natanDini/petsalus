package br.com.petsalus.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record EspecieAdd(

        @NotBlank(message = "O nome é obrigatório.")
        String nome) {
}
