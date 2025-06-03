package br.com.petsalus.validators;

import br.com.petsalus.annotations.EmailUnico;
import br.com.petsalus.annotations.UsernameUnico;
import br.com.petsalus.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameUnicoValidator implements ConstraintValidator<UsernameUnico, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(username);
    }
}