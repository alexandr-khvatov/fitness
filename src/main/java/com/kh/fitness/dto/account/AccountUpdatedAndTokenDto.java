package com.kh.fitness.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class AccountUpdatedAndTokenDto {
    Long id;
    String firstname;
    String patronymic;
    String lastname;
    String email;
    String phone;
    LocalDate birthDate;
    String token;
}