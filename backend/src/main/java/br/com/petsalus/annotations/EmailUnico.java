package br.com.petsalus.annotations;

import br.com.petsalus.validators.EmailUnicoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailUnicoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnico {
    String message() default "E-mail já está em uso.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}