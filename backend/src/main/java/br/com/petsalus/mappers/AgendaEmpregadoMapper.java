package br.com.petsalus.mappers;

import br.com.petsalus.dtos.response.AgendaEmpregado;
import br.com.petsalus.entities.ServicoAgenda;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class AgendaEmpregadoMapper {

    private static final NumberFormat FORMATO_BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public AgendaEmpregado map(ServicoAgenda agendamento) {

        return AgendaEmpregado.builder()
                .id(agendamento.getId())
                .dataHoraAgendada(agendamento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .clienteNome(agendamento.getPet().getTutor().getNome())
                .petNome(agendamento.getPet().getNome())
                .especie(agendamento.getPet().getEspecie().getNome())
                .raca(agendamento.getPet().getRaca().getNome())
                .servico(agendamento.getServico().getNome())
                .preco(formatarPreco(agendamento.getServico().getPreco()))
                .tempoServicoMin(agendamento.getServico().getTempoServicoMin())
                .status(agendamento.getStatus())
                .build();
    }

    public static List<AgendaEmpregado> map(List<ServicoAgenda> agendamentos) {
        return agendamentos.stream()
                .map(AgendaEmpregadoMapper::map)
                .collect(Collectors.toList());
    }

    private static String formatarPreco(BigDecimal preco) {
        return preco != null ? FORMATO_BRL.format(preco) : null;
    }
}