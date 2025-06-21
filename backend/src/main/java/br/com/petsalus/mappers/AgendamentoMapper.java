package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.Agendamento;
import br.com.petsalus.entities.ServicoAgenda;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class AgendamentoMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public Agendamento map(ServicoAgenda agendamento) {

        return Agendamento.builder()
                .dataHoraAgendada(agendamento.getDataHora())
                .empresa(agendamento.getServico().getEmpresa().getNome())
                .servico(agendamento.getServico().getNome())
                .preco(formatarPreco(agendamento.getServico().getPreco()))
                .tempoServicoMin(agendamento.getServico().getTempoServicoMin())
                .build();
    }

    public static List<Agendamento> map(List<ServicoAgenda> agendamentos) {
        return agendamentos.stream()
                .map(AgendamentoMapper::map)
                .collect(Collectors.toList());
    }

    private static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}