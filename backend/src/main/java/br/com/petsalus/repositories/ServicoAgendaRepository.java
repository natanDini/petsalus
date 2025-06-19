package br.com.petsalus.repositories;

import br.com.petsalus.entities.ServicoAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoAgendaRepository extends JpaRepository<ServicoAgenda, Long> {

}