package com.kh.fitness.dto.account;

import com.kh.fitness.validation.Password;
import com.kh.fitness.validation.Phone;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Data
@Value
public class LoginDto {
    @Phone
    String username;
    @Password
    String password;
}
