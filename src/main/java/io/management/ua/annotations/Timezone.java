package io.management.ua.annotations;


import io.management.ua.processors.TimezoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimezoneValidator.class)
public @interface Timezone  {
    String message() default "Timezone is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
