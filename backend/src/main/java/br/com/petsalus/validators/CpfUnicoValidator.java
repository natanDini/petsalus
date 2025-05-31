package br.com.petsalus.validators;

import br.com.petsalus.annotations.CpfUnico;
import br.com.petsalus.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CpfUnicoValidator implements ConstraintValidator<CpfUnico, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return !userRepository.existsByCpf(cpf);
    }
}