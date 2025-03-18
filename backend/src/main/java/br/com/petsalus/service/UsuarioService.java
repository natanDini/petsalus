package br.com.petsalus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.petsalus.model.Usuario;
import br.com.petsalus.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para buscar usuário pelo nome
    public Usuario buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para buscar usuário por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para verificar se o nome do usuário já existe
    public boolean verificarExistenciaNome(String nome) {
        return usuarioRepository.existsByNome(nome);
    }
}
