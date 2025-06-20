package br.com.petsalus.repositories;

import br.com.petsalus.entities.ServicoAgendaEmpregado;
import br.com.petsalus.entities.User;
import br.com.petsalus.enums.ServicoAgendaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ServicoAgendaEmpregadoRepository extends JpaRepository<ServicoAgendaEmpregado, Long> {

    // ServicoAgendaEmpregadoRepository
    @Query("""
   select sae
     from ServicoAgendaEmpregado sae
    join sae.servicoAgenda sa
    where sae.empregado in :empregados
      and sa.status <> :cancelado
      and sa.dataHora between :inicio and :fim
   """)
    List<ServicoAgendaEmpregado> buscarEntreDatas(@Param("empregados") Set<User> empregados,
                                                  @Param("inicio") LocalDateTime inicio,
                                                  @Param("fim") LocalDateTime fim,
                                                  @Param("cancelado") ServicoAgendaStatus cancelado);
    @Query("""
       select sae
         from ServicoAgendaEmpregado sae
    join sae.servicoAgenda sa
        where sae.empregado   = :emp
          and sa.status      <> :cancelado
          and sa.dataHora     < :fimRequerido
          and sa.dataHora + sa.servico.tempoServicoMin * 1 minute > :inicioRequerido
    """)
    List<ServicoAgendaEmpregado> encontrarConflitos(
            @Param("emp") User empregado,
            @Param("inicioRequerido") LocalDateTime inicio,
            @Param("fimRequerido") LocalDateTime fim,
            @Param("cancelado") ServicoAgendaStatus cancelado);
}
