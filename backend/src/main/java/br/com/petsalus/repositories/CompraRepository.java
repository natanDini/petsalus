package br.com.petsalus.repositories;

import br.com.petsalus.entities.Compra;
import br.com.petsalus.entities.Empresa;
import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("""
    SELECT DISTINCT ic.compra
    FROM ItemCompra ic
    WHERE ic.produto.empresa = :empresa
    ORDER BY ic.compra.dataHora DESC
    """)
    List<Compra> findComprasByEmpresa(@Param("empresa") Empresa empresa);

    List<Compra> findByTutorOrderByDataHoraDesc(User tutor);
}