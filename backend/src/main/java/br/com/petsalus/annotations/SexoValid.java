package br.com.petsalus.annotations;

import br.com.petsalus.validators.SexoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SexoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface SexoValid {
    String message() default "Sexo inválido. Permitido: MASCULINO, FEMININO e NAO_IDENTIFICADO.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}