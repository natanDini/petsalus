package br.com.petsalus.dtos.response;

import br.com.petsalus.enums.ServicoAgendaStatus;
import lombok.Builder;

@Builder
public record AgendaEmpregado(
        Long id,
        String dataHoraAgendada,
        String clienteNome,
        String petNome,
        String especie,
        String raca,
        String servico,
        String preco,
        Long tempoServicoMin,
        ServicoAgendaStatus status
) {
}
