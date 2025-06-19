package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.EmpresaRes;
import br.com.petsalus.entities.Empresa;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EmpresaResMapper {

    public EmpresaRes map(Empresa empresa) {

        String fotoBase64 = empresa.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(empresa.getFotoPerfil())
                : null;

        return EmpresaRes.builder()
                .id(empresa.getId())
                .cnpj(empresa.getCnpj())
                .nome(empresa.getNome())
                .email(empresa.getEmail())
                .telefone(empresa.getTelefone())
                .descricao(empresa.getDescricao())
                .fotoPerfil(fotoBase64)
                .modeloComercial(empresa.getModeloComercial().getDescricao())
                .trabalhaVinteQuatroHoras(empresa.isTrabalhaVinteQuatroHoras())
                .horaAbertura(empresa.getHoraAbertura())
                .horaEncerramento(empresa.getHoraEncerramento())
                .endereco(empresa.getEndereco())
                .build();
    }

    public static List<EmpresaRes> map(List<Empresa> empresas) {
        return empresas.stream()
                .map(EmpresaResMapper::map)
                .collect(Collectors.toList());
    }
}