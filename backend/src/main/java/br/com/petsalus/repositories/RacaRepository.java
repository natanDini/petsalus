package br.com.petsalus.repositories;

import br.com.petsalus.entities.Especie;
import br.com.petsalus.entities.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {

    List<Raca> findByEspecie(Especie especie);
}