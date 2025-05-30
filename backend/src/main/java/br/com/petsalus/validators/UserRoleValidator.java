package br.com.petsalus.validators;

import br.com.petsalus.annotations.UserRoleValid;
import br.com.petsalus.enums.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class UserRoleValidator implements ConstraintValidator<UserRoleValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(UserRole.values())
                .anyMatch(role -> role.name().equalsIgnoreCase(value));
    }
}