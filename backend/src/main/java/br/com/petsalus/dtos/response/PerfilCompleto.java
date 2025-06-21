package br.com.petsalus.dtos.response;

import br.com.petsalus.entities.Endereco;
import lombok.Builder;

@Builder
public record PerfilCompleto (

        Long id,
        String cpf,
        String nome,
        String email,
        String username,
        String telefone,
        Endereco endereco,
        String fotoPerfil
        ){
}