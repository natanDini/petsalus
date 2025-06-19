package br.com.petsalus.dtos.request;

import br.com.petsalus.enums.TipoServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicoAdd (

        @NotBlank(message = "O nome do serviço é obrigatório.")
        String nome,

        @NotNull(message = "O Preço do serviço é obrigatório.")
        BigDecimal preco,

        @NotBlank(message = "A descrição do serviço é obrigatória.")
        String descricao,

        @NotNull(message = "É necessário informar se o serviço é público ou não.")
        Boolean isPublico,

        @NotNull(message = "O tempo de serviço (em minutos) é obrigatório.")
        @Positive(message = "O tempo de serviço deve ser um valor positivo.")
        Long tempoServicoMin,

        @NotNull(message = "O tipo de serviço é obrigatório.")
        TipoServico tipoServico){
}