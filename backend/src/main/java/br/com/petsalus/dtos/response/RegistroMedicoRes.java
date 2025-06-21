package br.com.petsalus.dtos.response;

import lombok.Builder;

@Builder
public record RegistroMedicoRes(
    String empresa,
    String empresaCnpj,
    String veterinario,
    String dataHora,
    String tituloServico,
    String descricao,
    String tipoServico,
    String empresaFotoPerfil
) {
}
