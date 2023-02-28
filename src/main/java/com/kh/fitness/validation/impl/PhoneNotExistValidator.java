package com.kh.fitness.validation.impl;

import com.kh.fitness.service.user.UserService;
import com.kh.fitness.validation.PhoneNotExist;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


@RequiredArgsConstructor
public class PhoneNotExistValidator implements ConstraintValidator<PhoneNotExist, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(userService::existsUserByPhone)
                .map(exist -> !exist)
                .orElse(false);
    }
}