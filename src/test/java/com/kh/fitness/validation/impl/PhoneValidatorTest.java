package com.kh.fitness.validation.impl;

import com.kh.fitness.validation.Phone;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import javax.validation.*;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneValidatorTest {

    private static Validator validator;
    private static ValidatorFactory factory;

    @RequiredArgsConstructor
    static class CheckFieldValidation {
        @Phone
        private final String phone;
    }

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    void checkMessageAndPropertyPathAndSizeConstraintIsEqual() {
        Set<ConstraintViolation<PhoneValidatorTest.CheckFieldValidation>> violations = validator.validate(new PhoneValidatorTest.CheckFieldValidation("invalid_format"));

        boolean hasExpectedPropertyPath = violations.stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .anyMatch("phone"::equals);
        boolean hasExpectedViolationMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch("{validation.phone.invalid}"::equals);

        assertAll(
                () -> assertThat(violations).hasSize(1),
                () -> assertTrue(hasExpectedPropertyPath),
                () -> assertTrue(hasExpectedViolationMessage));
    }

    @ParameterizedTest
    @MethodSource(value = "getArgForValidPhoneTest")
    void validPhoneTest(String phone) {
        Set<ConstraintViolation<PhoneValidatorTest.CheckFieldValidation>> violations = validator.validate(new PhoneValidatorTest.CheckFieldValidation(phone));
        assertThat(violations).isEmpty();
    }

    static Stream<Arguments> getArgForValidPhoneTest() {
        return Stream.of(
                Arguments.of("+78005553535"),
                Arguments.of("88005553535")
        );
    }

    @ParameterizedTest
    @NullSource
    @MethodSource(value = "getArgForForInvalidPhoneTest")
    void invalidPhoneTest(String phone) {
        Set<ConstraintViolation<PhoneValidatorTest.CheckFieldValidation>> violations = validator.validate(new PhoneValidatorTest.CheckFieldValidation(phone));
        assertThat(violations).hasSize(1);
    }

    static Stream<Arguments> getArgForForInvalidPhoneTest() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("+88005553535"),
                Arguments.of("78005553535"),
                Arguments.of("+780055535355"),
                Arguments.of("+7f005553535"),
                Arguments.of("+7f005553535")
        );
    }
}