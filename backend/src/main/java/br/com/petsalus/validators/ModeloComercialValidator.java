package br.com.petsalus.validators;

import br.com.petsalus.annotations.ModeloComercialValid;
import br.com.petsalus.annotations.SexoValid;
import br.com.petsalus.enums.ModeloComercial;
import br.com.petsalus.enums.Sexo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ModeloComercialValidator implements ConstraintValidator<ModeloComercialValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(ModeloComercial.values())
                .anyMatch(mc -> mc.name().equalsIgnoreCase(value));
    }
}