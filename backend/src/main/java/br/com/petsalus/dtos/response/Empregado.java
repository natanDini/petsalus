package br.com.petsalus.dtos.response;

import br.com.petsalus.entities.Endereco;
import br.com.petsalus.enums.UserRole;
import lombok.Builder;

@Builder
public record Empregado (
        Long id,
        String cpf,
        String nome,
        String email,
        String username,
        String telefone,
        UserRole userRole,
        Endereco endereco,
        String fotoPerfil
){
}
