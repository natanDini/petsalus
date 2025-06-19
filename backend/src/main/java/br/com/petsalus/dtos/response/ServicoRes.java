package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record ServicoRes (
        
     String nome,
     String preco,
     String descricao,
     Long tempoServicoMin,
     String tipoServico){
}