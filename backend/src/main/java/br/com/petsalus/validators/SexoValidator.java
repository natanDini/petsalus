package br.com.petsalus.validators;

import br.com.petsalus.annotations.SexoValid;
import br.com.petsalus.enums.Sexo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class SexoValidator implements ConstraintValidator<SexoValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(Sexo.values())
                .anyMatch(sexo -> sexo.name().equalsIgnoreCase(value));
    }
}
