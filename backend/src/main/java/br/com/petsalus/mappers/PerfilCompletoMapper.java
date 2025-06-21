package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.PerfilCompleto;
import br.com.petsalus.dtos.response.PerfilPequeno;
import br.com.petsalus.entities.User;
import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class PerfilCompletoMapper {

    public PerfilCompleto map(User user) {

        String fotoBase64 = user.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getFotoPerfil())
                : null;

        return PerfilCompleto.builder()
                .id(user.getId())
                .cpf(user.getCpf())
                .nome(user.getNome())
                .email(user.getEmail())
                .username(user.getUsername())
                .telefone(user.getTelefone())
                .endereco(user.getEndereco())
                .fotoPerfil(fotoBase64)
                .build();
    }
}