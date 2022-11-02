package com.kh.fitness.dto;

import com.kh.fitness.validation.Phone;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class FreePassRequestCreateDto {
    @NotBlank
    String firstname;

    String lastname;

    @Phone
    String phone;

    @Email
    String email;
}

