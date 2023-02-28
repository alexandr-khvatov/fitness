package com.kh.fitness.dto.user;

import com.kh.fitness.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class UserCreatedDto {
    Long id;
    String firstname;
    String patronymic;
    String lastname;
    String email;
    String phone;
    LocalDate birthDate;
    Set<Role> roles;
}