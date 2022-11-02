package com.kh.fitness.validation.impl;

import com.kh.fitness.validation.FieldMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            var field = value.getClass().getDeclaredField(firstFieldName);
            field.setAccessible(true);
            Object valueFirstField = field.get(value);

            field = value.getClass().getDeclaredField(secondFieldName);
            field.setAccessible(true);
            Object valueSecondField = field.get(value);

            valid = valueFirstField == null && valueSecondField == null
                    || valueFirstField != null && valueFirstField.equals(valueSecondField);
        } catch (Exception ignore) {}

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
