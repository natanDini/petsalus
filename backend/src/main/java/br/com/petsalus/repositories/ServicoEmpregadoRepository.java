package br.com.petsalus.repositories;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Servico;
import br.com.petsalus.entities.ServicoEmpregado;
import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoEmpregadoRepository extends JpaRepository<ServicoEmpregado, Long> {

    boolean existsByServicoAndEmpregado(Servico servico, User empregado);

    ServicoEmpregado findByServicoAndEmpregado(Servico servico, User empregado);

    List<ServicoEmpregado> findByServico(Servico servico);

    @Query("""
    SELECT DISTINCT se.empregado
    FROM ServicoEmpregado se
    WHERE se.servico = :servico
    """)
    List<User> findEmpregadosByServico(@Param("servico") Servico servico);

    @Query("""
        select distinct s.tempoServicoMin
          from ServicoEmpregado se
          join se.servico s
         where s.empresa = :empresa
    """)
    List<Long> findDistinctDurationsByEmpresa(@Param("empresa") Empresa empresa);
}