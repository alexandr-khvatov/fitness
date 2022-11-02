package com.kh.fitness.dto.account;

import com.kh.fitness.validation.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@FieldMatch(first = "newPassword", second = "confirmPassword", message = "Пароли не совпадают")
public class AccountChangePasswordDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}