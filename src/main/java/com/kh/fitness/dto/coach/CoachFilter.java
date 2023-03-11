package com.kh.fitness.dto.coach;

import java.time.LocalDate;

public record CoachFilter(
        String firstname,
        String patronymic,
        String lastname,
        String phone,
        String email,
        LocalDate birthDate) {
}