package io.management.ua.annotations;


import io.management.ua.processors.AtLeastOneIsValidAnnotationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneIsValidAnnotationValidator.class)
public @interface AtLeastOneIsValid {
    String message() default "All validated fields are invalid";
    String[] patterns() default {"^[a-zA-Z0-9]*$"};
    String[] fields() default {};
    Class<?> type() default String.class;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
