package io.management.ua.annotations.processors;

import io.management.ua.annotations.Timezone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.TimeZone;

public class TimezoneValidator implements ConstraintValidator<Timezone, String> {
    @Override
    public void initialize(Timezone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
      return Arrays.asList(TimeZone.getAvailableIDs()).contains(value);
    }
}
