package br.com.petsalus.repositories;

import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByEmpresaOrderByNomeAsc(Empresa empresa);
    List<Produto> findByEmpresaAndDisponivelIsTrue(Empresa empresa);
}