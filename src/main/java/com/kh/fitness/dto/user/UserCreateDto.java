package com.kh.fitness.dto.user;

import com.kh.fitness.validation.EmailNotExist;
import com.kh.fitness.validation.Password;
import com.kh.fitness.validation.Phone;
import com.kh.fitness.validation.PhoneNotExist;
import com.kh.fitness.validation.group.CheckNotExistAfter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank
    String firstname;

    @NotBlank
    String patronymic;

    @NotBlank
    String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotBlank
    @Password
    String password;

    @Phone
    @PhoneNotExist(groups = CheckNotExistAfter.class)
    String phone;

    @Email
    @EmailNotExist(groups = CheckNotExistAfter.class)
    String email;

    @NotNull
    Long gymId;

    @NotEmpty
    @NotNull
    Set<Long> roles;
}