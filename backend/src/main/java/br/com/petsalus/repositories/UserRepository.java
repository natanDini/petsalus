package br.com.petsalus.repositories;

import br.com.petsalus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCpf(String cpf);
    Optional<User> findByUsername(String username);

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByTelefone(String telefone);
}