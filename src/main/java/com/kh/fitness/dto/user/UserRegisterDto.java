package com.kh.fitness.dto.user;

import com.kh.fitness.validation.EmailNotExist;
import com.kh.fitness.validation.Password;
import com.kh.fitness.validation.Phone;
import com.kh.fitness.validation.PhoneNotExist;
import com.kh.fitness.validation.group.CheckNotExistAfter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserRegisterDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotBlank
    String firstname;

    @NotBlank
    String patronymic;

    @NotBlank
    String lastname;

    @NotBlank
    @Password
    String password;

    @Phone
    @PhoneNotExist(groups = CheckNotExistAfter.class)
    String phone;

    @Email
    @EmailNotExist(groups = CheckNotExistAfter.class)
    String email;
}