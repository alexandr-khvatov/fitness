package com.kh.fitness.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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
    Set<Long> roles;
}
