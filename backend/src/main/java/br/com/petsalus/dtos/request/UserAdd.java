package br.com.petsalus.dtos.request;

import br.com.petsalus.annotations.UserRoleValid;
import br.com.petsalus.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record UserAdd(

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "CPF inválido.")
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @NotBlank(message = "O nome de usuário é obrigatório.")
        String username,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "Formato de telefone inválido.")
        String telefone,

        @NotNull(message = "O papel do usuário é obrigatório.")
        @UserRoleValid
        String userRole,

        @Valid
        EnderecoAdd endereco) {
}