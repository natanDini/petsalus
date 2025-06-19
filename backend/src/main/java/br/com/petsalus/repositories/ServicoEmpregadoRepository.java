package br.com.petsalus.repositories;

import br.com.petsalus.entities.ServicoEmpregado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoEmpregadoRepository extends JpaRepository<ServicoEmpregado, Long> {
}