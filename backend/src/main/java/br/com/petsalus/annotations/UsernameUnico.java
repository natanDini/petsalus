package br.com.petsalus.annotations;

import br.com.petsalus.validators.CpfUnicoValidator;
import br.com.petsalus.validators.UsernameUnicoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameUnicoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnico {
    String message() default "CPF já está em uso.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}