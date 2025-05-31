package br.com.petsalus.annotations;

import br.com.petsalus.validators.CpfUnicoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfUnicoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfUnico {
    String message() default "CPF já está em uso.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}