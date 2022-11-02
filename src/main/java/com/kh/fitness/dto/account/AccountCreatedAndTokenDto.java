package com.kh.fitness.dto.account;

import com.kh.fitness.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class AccountCreatedAndTokenDto {
    Long id;
    String firstname;
    String patronymic;
    String lastname;
    String email;
    String phone;
    LocalDate birthDate;
    Set<Role> roles;
    String token;
}

// TODO Change Role view

//  FROM:
//  "roles": [
//        {
//            "id": 4,
//            "name": "CUSTOMER",
//            "authority": "CUSTOMER"
//        }
//    ],

// //  TO :
//  "roles": [
//        {
//            "name": "CUSTOMER",
//        }
//    ],