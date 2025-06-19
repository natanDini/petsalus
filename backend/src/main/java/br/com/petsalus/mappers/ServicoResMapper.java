package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.ServicoRes;
import br.com.petsalus.entities.Servico;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class ServicoResMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public ServicoRes map(Servico servico) {

        return ServicoRes.builder()
                .nome(servico.getNome())
                .descricao(servico.getDescricao())
                .preco(formatarPreco(servico.getPreco()))
                .tempoServicoMin(servico.getTempoServicoMin())
                .tipoServico(servico.getTipoServico().getDescricao())
                .build();
    }

    public static List<ServicoRes> map(List<Servico> servicos) {
        return servicos.stream()
                .map(ServicoResMapper::map)
                .collect(Collectors.toList());
    }

    private static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}