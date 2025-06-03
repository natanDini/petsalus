package br.com.petsalus.validators;


import br.com.petsalus.annotations.TelefoneUnico;
import br.com.petsalus.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelefoneUnicoValidator implements ConstraintValidator<TelefoneUnico, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        return !userRepository.existsByTelefone(telefone);
    }
}