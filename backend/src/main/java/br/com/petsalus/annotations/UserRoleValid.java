package br.com.petsalus.annotations;

import br.com.petsalus.validators.UserRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserRoleValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserRoleValid {
    String message() default "Papel de usuário inválido. Valores permitidos: TUTOR, MEDICO, ADMINISTRADOR.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}