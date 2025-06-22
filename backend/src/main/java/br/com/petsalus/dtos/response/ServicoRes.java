package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record ServicoRes (
     Long id,
     String nome,
     String preco,
     String descricao,
     Long tempoServicoMin,
     String tipoServico){
}