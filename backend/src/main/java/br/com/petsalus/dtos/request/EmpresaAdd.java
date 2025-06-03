package br.com.petsalus.dtos.request;

import br.com.petsalus.annotations.ModeloComercialValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaAdd (

        @NotBlank(message = "O CNPJ é obrigatório.")
        @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "Formato de CNPJ inválido. Use o formato xx.xxx.xxx/xxxx-xx.")
        @CNPJ(message = "CNPJ inválido.")
        String cnpj,

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}", message = "Número de celular inválido. O formato deve ser (XX) 9XXXX-XXXX")
        String telefone,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotBlank(message = "O modelo comercial é obrigatório.")
        @ModeloComercialValid
        String modeloComercial,

        @NotNull(message = "O campo trabalhaVinteQuatroHoras é obrigatório.")
        Boolean trabalhaVinteQuatroHoras,

        @Valid
        EnderecoAdd endereco){
}