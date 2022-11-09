package com.kh.fitness.dto.coach;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachReadDto {
    private Long id;
    private String firstname;
    private String patronymic;
    private String lastname;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String specialization;
    private String image;
    private String description;
}