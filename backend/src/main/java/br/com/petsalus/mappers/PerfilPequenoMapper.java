package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.PerfilPequeno;
import br.com.petsalus.entities.User;
import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class PerfilPequenoMapper {

    public PerfilPequeno map(User user) {

        String fotoBase64 = user.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getFotoPerfil())
                : null;

        return PerfilPequeno.builder()
                .id(user.getId())
                .nome(user.getNome())
                .fotoPerfil(fotoBase64)
                .build();
    }
}