package com.kh.fitness.validation.impl;

import com.kh.fitness.validation.Phone;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private static final String REGEX_PHONE = "^(\\+7|8)\\d{10}$";

    @Override
    public boolean isValid(@Validated String value, ConstraintValidatorContext context) {
        if (isNull(value)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_PHONE);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}