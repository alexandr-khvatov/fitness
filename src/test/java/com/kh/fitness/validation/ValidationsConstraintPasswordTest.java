package com.kh.fitness.validation;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.*;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationsConstraintPasswordTest {

    private static Validator validator;
    private static ValidatorFactory factory;

    @RequiredArgsConstructor
    static class Invalid {
        @Password
        private final String password;
    }

    @BeforeEach
    void setUp() {
//        Locale.setDefault(Locale.ENGLISH);  //expecting english error messages
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    void checkMessageAndPropertyPathAndSizeConstraintIsEqual_1() {
        Set<ConstraintViolation<Invalid>> violations = validator.validate(new Invalid("password1"));

        boolean hasExpectedPropertyPath = violations.stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .anyMatch("password"::equals);
        boolean hasExpectedViolationMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch("{validation.password.invalid}"::equals);

        assertAll(
                () -> assertThat(violations).hasSize(1),
                () -> assertTrue(hasExpectedPropertyPath),
                () -> assertTrue(hasExpectedViolationMessage));
    }

    @ParameterizedTest
    @MethodSource(value = "getArgForValidPasswordTest")
    void validPasswordTest(String pwd) {
        Set<ConstraintViolation<Invalid>> violations = validator.validate(new Invalid(pwd));
        assertThat(violations).isEmpty();
    }

    static Stream<Arguments> getArgForValidPasswordTest() {
        return Stream.of(
                Arguments.of("1-RTKfmfj1"),
                Arguments.of("pas@sw9orD"),
                Arguments.of("nsFdj123*90"),
                Arguments.of("@fjkk&D*972"),
                Arguments.of("2fkjdnfFD!"),
                Arguments.of("&dksn3fsKHd"),
                Arguments.of("49F-3cR-yzk-J2u"),
                Arguments.of("NXEPwSqmRh6zirP48*mA"),
                Arguments.of("Lu$jYbn2"),
                Arguments.of("f^azH6Mn"),
                Arguments.of("MrjcySoY9NiG&x5"),
                Arguments.of("eF&vqvWQW6#Zt69Pw#&c"),
                Arguments.of("!HxwXaU5HZzZE4@7q9^Z"));
    }

    @ParameterizedTest
    @MethodSource(value = "getArgForForInvalidPasswordTest")
    void invalidPasswordTest(String pwd) {
        Set<ConstraintViolation<Invalid>> violations = validator.validate(new Invalid(pwd));
assertThat(violations).hasSize(1);
    }

    static Stream<Arguments> getArgForForInvalidPasswordTest() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("             "),
                Arguments.of(" "),
                Arguments.of("paSSword-1."),
                Arguments.of("size7-D"),
                Arguments.of("size21-DDDDDDDDDDDDDD"),
                Arguments.of("nsfdj123*90"),
                Arguments.of("@fjkk&*972"),
                Arguments.of("2fkjdnfFDsfksjg"),
                Arguments.of("1-dskjnflsk!"),
                Arguments.of("L7VqwNmmpgvu6QPJb!e4Fsw"),
                Arguments.of("&dksnfsKHd"));
    }
}
