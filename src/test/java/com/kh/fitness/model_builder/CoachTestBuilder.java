package com.kh.fitness.model_builder;

import com.kh.fitness.entity.Coach;
import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.Training;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CoachTestBuilder {
    private static final Long id = 1L;
    private static final String firstname = "coach_firstname";
    private static final String patronymic = "coach_patronymic";
    private static final String lastname = "coach_lastname";
    private static final String email = "coach_email";
    private static final String phone = "coach_phone";
    private static final String specialization = "coach_specialization";
    private static final String description = "coach_description";
    private static final String image = "coach_image";
    private static final Set<Training> trainings = new HashSet<>();
    private static final Gym gym = new Gym();
    private static final LocalDate birthDate = LocalDate.now();

    public static Coach getCoach() {
        return Coach.builder()
                .id(id)
                .firstname(firstname)
                .patronymic(patronymic)
                .lastname(lastname)
                .email(email)
                .phone(phone)
                .specialization(specialization)
                .description(description)
                .image(image)
                .birthDate(birthDate)
                .gym(gym)
                .trainings(trainings)
                .build();
    }
}