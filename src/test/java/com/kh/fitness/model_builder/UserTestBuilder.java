package com.kh.fitness.model_builder;

import com.kh.fitness.entity.user.Role;
import com.kh.fitness.entity.user.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserTestBuilder {
    private static final Long id = 1L;
    private static final String firstname = "coach_firstname";
    private static final String patronymic = "coach_patronymic";
    private static final String lastname = "coach_lastname";
    private static final String email = "coach_email";
    private static final String phone = "coach_phone";
    private static final String image = "coach_image";
    private static final String password = "coach_password";
    private static final LocalDate birthDate = LocalDate.now();
    private static final Set<Role> roles = new HashSet<>();

    public static User getUser() {
        return User.builder()
                .id(id)
                .firstname(firstname)
                .patronymic(patronymic)
                .lastname(lastname)
                .email(email)
                .password(password)
                .phone(phone)
                .birthDate(birthDate)
                .image(image)
                .build();
    }
}