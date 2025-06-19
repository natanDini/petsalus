package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.EmpresaShortRes;
import br.com.petsalus.entities.Empresa;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EmpresaShortResMapper {

    public static EmpresaShortRes map(Empresa empresa) {
        String fotoBase64 = empresa.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(empresa.getFotoPerfil())
                : null;

        return EmpresaShortRes.builder()
                .id(empresa.getId())
                .nome(empresa.getNome())
                .descricao(empresa.getDescricao())
                .fotoPerfil(fotoBase64)
                .modeloComercial(empresa.getModeloComercial().getDescricao())
                .trabalhaVinteQuatroHoras(empresa.isTrabalhaVinteQuatroHoras())
                .horaAbertura(empresa.getHoraAbertura())
                .horaEncerramento(empresa.getHoraEncerramento())
                .build();
    }

    public static List<EmpresaShortRes> map(List<Empresa> empresas) {
        return empresas.stream()
                .map(EmpresaShortResMapper::map)
                .collect(Collectors.toList());
    }
}