package com.kh.fitness.dto.user;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {
    Long id;
    String firstname;
    String patronymic;
    String lastname;
    String email;
    String phone;
    LocalDate birthDate;
}
