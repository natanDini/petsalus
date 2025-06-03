package br.com.petsalus.dtos.request;

import br.com.petsalus.annotations.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record UserAdd(

        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato de CPF inválido. Use o formato xxx.xxx.xxx-xx.")
        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "CPF inválido.")
        @CpfUnico
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        @EmailUnico
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @NotBlank(message = "O nome de usuário é obrigatório.")
        @UsernameUnico
        String username,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}", message = "Número de celular inválido. O formato deve ser (XX) 9XXXX-XXXX")
        @TelefoneUnico
        String telefone,

        @NotNull(message = "O papel do usuário é obrigatório.")
        @UserRoleValid
        String userRole,

        @Valid
        EnderecoAdd endereco) {
}