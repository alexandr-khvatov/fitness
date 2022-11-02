package com.kh.fitness.dto.user;

import com.kh.fitness.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateWithoutImageDto {
    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotBlank
    String firstname;

    @NotBlank
    String patronymic;

    @NotBlank
    String lastname;

    @NotBlank
    String password;

    @Phone
    @NotBlank
    String phone;

    @NotEmpty
    Set<Long> roles ;
}