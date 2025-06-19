package br.com.petsalus.utils;

import br.com.petsalus.entities.User;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário informado não encontrado."));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuário informado não encontrado."));
    }

    public User findByCpf(String cpf){
        return userRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Usuário informado não encontrado."));
    }

    public User findByJwt(Jwt jwt){
        String username = jwt.getSubject();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuário logado não encontrado."));
    }
}