package io.management.ua.processors;

import io.management.ua.annotations.AtLeastOneIsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.List;

public class AtLeastOneIsValidAnnotationValidator implements ConstraintValidator<AtLeastOneIsValid, Object> {
    private String[] fields;
    private Boolean fieldValidationIsPassed = false;
    private String[] pattern;
    private Class<?> type;
    private final List<Class<?>> allowedTypes =
            List.of(Integer.class, Long.class, String.class, Boolean.class, Character.class, Short.class, Byte.class, Float.class, Double.class);

    @Override
    public void initialize(AtLeastOneIsValid constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
        this.pattern = constraintAnnotation.patterns();
        this.type = constraintAnnotation.type();

        if (fields.length <= 1) {
            throw new IllegalArgumentException("Invalid fields number, must be at least several fields to validate in group");
        }
        if (!allowedTypes.contains(type)) {
            throw new IllegalArgumentException(String.format("Selected type %s is not supported", type));
        }
        if (pattern.length != 1 && pattern.length != fields.length) {
            throw new IllegalArgumentException("Invalid pattern number, must be one for all fields or one for each field");
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            for (int index = 0; index < fields.length; index++) {
                String fieldName = fields[index];
                Field field = value.getClass().getDeclaredField(fieldName);

                if (field.getType().equals(type)) {
                    field.setAccessible(true);
                    if (pattern.length == 1) {
                        if (field.get(value) != null && String.valueOf(field.get(value)).matches(pattern[0])) {
                            fieldValidationIsPassed = true;
                        }
                    } else {
                        if (field.get(value) != null && String.valueOf(field.get(value)).matches(pattern[index])) {
                            fieldValidationIsPassed = true;
                        }
                    }
                } else {
                    throw new IllegalArgumentException(String.format("Invalid field selected for validation, type mismatch, field %s, type %s, target type %s",
                            fieldName, field.getType(), type));
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return fieldValidationIsPassed;
    }
}
