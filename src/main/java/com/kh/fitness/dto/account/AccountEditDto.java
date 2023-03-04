package com.kh.fitness.dto.account;

import com.kh.fitness.validation.Phone;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEditDto {
    @NotBlank
    String firstname;

    @NotBlank
    String patronymic;

    @NotBlank
    String lastname;

    @Phone
    String phone;

    @Email
    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotNull
    Long gymId;
}