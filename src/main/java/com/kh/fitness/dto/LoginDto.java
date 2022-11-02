package com.kh.fitness.dto;

import com.kh.fitness.validation.Password;
import com.kh.fitness.validation.Phone;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
public class LoginDto {
    @Phone
    String username;
    @Password
    String password;
}
