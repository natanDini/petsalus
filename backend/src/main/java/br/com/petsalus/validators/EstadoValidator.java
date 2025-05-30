package br.com.petsalus.validators;

import br.com.petsalus.annotations.EstadoValid;
import br.com.petsalus.enums.Estados;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EstadoValidator implements ConstraintValidator<EstadoValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(Estados.values())
                .anyMatch(estado -> estado.name().equalsIgnoreCase(value));
    }
}
