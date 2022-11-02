package com.kh.fitness.validation.impl;

import com.kh.fitness.service.UserService;
import com.kh.fitness.validation.EmailNotExist;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class EmailNotExistValidator implements ConstraintValidator<EmailNotExist, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(userService::existsUserByEmail)
                .map(exist -> !exist)
                .orElse(false);
    }
}