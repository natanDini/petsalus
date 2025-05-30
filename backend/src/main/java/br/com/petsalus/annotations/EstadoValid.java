package br.com.petsalus.annotations;

import br.com.petsalus.validators.EstadoValidator;
import br.com.petsalus.validators.UserRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EstadoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EstadoValid {
    String message() default "Estado inválido. Formato permitido: UF.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}