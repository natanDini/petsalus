package br.com.petsalus.repositories;

import br.com.petsalus.entities.Carrinho;
import br.com.petsalus.entities.Produto;
import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    boolean existsByTutorAndProduto(User tutor, Produto produto);

    Optional<Carrinho> findByTutorAndProduto(User tutor, Produto produto);

    List<Carrinho> findByTutor(User user);
}