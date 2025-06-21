package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.RegistroMedicoRes;
import br.com.petsalus.entities.*;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RegistroMedicoResMapper {

    private static final DateTimeFormatter FORMATADOR_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public RegistroMedicoRes map(RegistroMedico registroMedico) {

        ServicoAgenda agenda = registroMedico.getServicoAgenda();
        User veterinario = agenda.getEmpregado();

        Empresa empresa = agenda.getServico().getEmpresa();

        String fotoBase64 = empresa.getFotoPerfil() != null
                ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(empresa.getFotoPerfil())
                : null;

        String dataHoraFormatada = agenda.getDataHora().format(FORMATADOR_BR);

        return RegistroMedicoRes.builder()
                .empresa(empresa.getNome())
                .empresaCnpj(empresa.getCnpj())
                .veterinario(veterinario.getNome())
                .dataHora(dataHoraFormatada)
                .tituloServico(agenda.getServico().getNome())
                .descricao(registroMedico.getDescricao())
                .tipoServico(registroMedico.getStatus().getDescricao())
                .empresaFotoPerfil(fotoBase64)
                .build();
    }

    public static List<RegistroMedicoRes> map(List<RegistroMedico> registros) {
        return registros.stream()
                .map(RegistroMedicoResMapper::map)
                .collect(Collectors.toList());
    }
}