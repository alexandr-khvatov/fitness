package com.kh.fitness.validation;

import com.kh.fitness.validation.impl.EmailNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = EmailNotExistValidator.class)
public @interface EmailNotExist {
    String message() default "{validation.email.already.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}