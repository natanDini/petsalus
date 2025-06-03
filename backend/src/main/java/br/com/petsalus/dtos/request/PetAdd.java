package br.com.petsalus.dtos.request;

import br.com.petsalus.annotations.SexoValid;
import jakarta.validation.constraints.*;

public record PetAdd(

        @NotNull(message = "Peso é obrigatório.")
        @Digits(integer = 3, fraction = 1, message = "O peso deve ter no máximo uma casa decimal e até 3 dígitos inteiros.")
        Float peso,

        @NotBlank(message = "Nome é obrigatório.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        @Size(max = 250, message = "A descricao deve ter no máximo 250 caracteres.")
        String descricao,

        @NotNull(message = "Idade é obrigatório.")
        @Min(value = 0, message = "A idade não pode ser negativa.")
        Integer idade,

        @NotBlank(message = "Sexo é obrigatório.")
        @SexoValid
        String sexo,

        @NotNull(message = "Raça é obrigatório.")
        Long racaId,

        @NotNull(message = "Espécie é obrigatório.")
        Long especieId) {
}
