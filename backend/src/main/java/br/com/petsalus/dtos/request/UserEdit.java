package br.com.petsalus.dtos.request;

import br.com.petsalus.annotations.EmailUnico;
import br.com.petsalus.annotations.TelefoneUnico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserEdit (

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}", message = "Número de celular inválido. O formato deve ser (XX) 9XXXX-XXXX")
        String telefone,

        @Valid
        EnderecoAdd endereco){
}
