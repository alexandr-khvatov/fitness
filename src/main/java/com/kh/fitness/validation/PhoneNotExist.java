package com.kh.fitness.validation;

import com.kh.fitness.validation.impl.PhoneNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = PhoneNotExistValidator.class)
public @interface PhoneNotExist {
    String message() default "{validation.phone.already.exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
