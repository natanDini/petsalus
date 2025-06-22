package br.com.petsalus.repositories;

import br.com.petsalus.entities.Compra;
import br.com.petsalus.entities.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

    List<ItemCompra> findByCompra(Compra compra);
}