package br.com.petsalus.repositories;

import br.com.petsalus.entities.Compra;
import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByTutorOrderByDataHoraDesc(User tutor);
}