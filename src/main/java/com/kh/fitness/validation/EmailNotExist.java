package com.kh.fitness.validation;

import com.kh.fitness.validation.impl.EmailNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = EmailNotExistValidator.class)
public @interface EmailNotExist {
    String message() default "{validation.email.already.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
