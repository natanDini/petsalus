package br.com.petsalus.annotations;

import br.com.petsalus.validators.EstadoValidator;
import br.com.petsalus.validators.ModeloComercialValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ModeloComercialValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModeloComercialValid {
    String message() default "Modelo comercial inválido. Permitido: CLINICA, PET_SHOP e CLINICA_E_PET_SHOP.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}