package br.com.petsalus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.petsalus.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo nome
    Optional<Usuario> findByNome(String nome);

    // Método para buscar um usuário pelo e-mail (se existir essa coluna)
    Optional<Usuario> findByEmail(String email);

    // Método para verificar se o usuário existe pelo nome
    boolean existsByNome(String nome);
}
