package br.com.petsalus.repositories;

import br.com.petsalus.entities.Pet;
import br.com.petsalus.entities.RegistroMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroMedicoRepository extends JpaRepository<RegistroMedico, Long> {

    List<RegistroMedico> findByPetOrderByServicoAgenda_DataHoraDesc(Pet pet);
}