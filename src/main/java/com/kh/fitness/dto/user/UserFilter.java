package com.kh.fitness.dto.user;

import java.time.LocalDate;

public record UserFilter(
        String firstname,
        String patronymic,
        String lastname,
        String phone,
        String email,
        Long role,
        LocalDate birthDate) {
}
