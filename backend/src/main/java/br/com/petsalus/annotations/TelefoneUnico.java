package br.com.petsalus.annotations;

import br.com.petsalus.validators.TelefoneUnicoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefoneUnicoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TelefoneUnico {
    String message() default "Telefone já está em uso.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}