package br.com.petsalus.dtos.request;

import br.com.petsalus.annotations.EstadoValid;
import br.com.petsalus.enums.Estados;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoAdd(

        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Formato de CEP inválido. Use o formato 00000-000.")
        String cep,

        @NotBlank(message = "O bairro é obrigatório.")
        String bairro,

        @NotBlank(message = "A cidade é obrigatório.")
        String cidade,

        @NotBlank(message = "O número é obrigatório.")
        @Digits(integer = 10, fraction = 0, message = "Número residencial deve ser um número inteiro.")
        String numero,

        @NotNull(message = "O estado é obrigatório.")
        @EstadoValid
        String estado,

        @NotBlank(message = "O endereço é obrigatório.")
        String endereco,

        String complemento) {
}
