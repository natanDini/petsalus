package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.Empregado;
import br.com.petsalus.entities.User;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EmpregadoMapper {

    public Empregado map(User user) {

        String fotoBase64 = user.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getFotoPerfil())
                : null;

        return Empregado.builder()
                .id(user.getId())
                .cpf(user.getCpf())
                .nome(user.getNome())
                .email(user.getEmail())
                .username(user.getUsername())
                .telefone(user.getTelefone())
                .userRole(user.getUserRole())
                .endereco(user.getEndereco())
                .fotoPerfil(fotoBase64)
                .build();
    }

    public static List<Empregado> map(List<User> users) {
        return users.stream()
                .map(EmpregadoMapper::map)
                .collect(Collectors.toList());
    }
}
