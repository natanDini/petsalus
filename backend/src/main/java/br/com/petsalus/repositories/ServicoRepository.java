package br.com.petsalus.repositories;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Servico;
import br.com.petsalus.enums.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByEmpresaOrderByNomeAsc(Empresa empresa);

    List<Servico> findByEmpresaAndTipoServicoOrderByNomeAsc(Empresa empresa, TipoServico tipoServico);

    List<Servico> findByEmpresaAndIsPublicoIsTrue(Empresa empresa);
}