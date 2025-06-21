package br.com.petsalus.repositories;

import br.com.petsalus.entities.Pet;
import br.com.petsalus.entities.ServicoAgenda;
import br.com.petsalus.enums.ServicoAgendaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoAgendaRepository extends JpaRepository<ServicoAgenda, Long> {

    List<ServicoAgenda> findByPetAndStatus(Pet pet, ServicoAgendaStatus status);
}