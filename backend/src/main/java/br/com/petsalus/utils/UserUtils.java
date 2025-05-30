package br.com.petsalus.utils;

import br.com.petsalus.entities.User;
import br.com.petsalus.exceptions.NotFoundException;
import br.com.petsalus.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User informado não encontrado."));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User informado não encontrado."));
    }
}