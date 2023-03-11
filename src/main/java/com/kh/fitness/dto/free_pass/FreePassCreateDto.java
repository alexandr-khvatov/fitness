package com.kh.fitness.dto.free_pass;

import com.kh.fitness.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreePassCreateDto {
    @NotBlank
    String firstName;

    String lastName;

    @Phone
    String phone;

    @Email
    String email;

    @NotNull
    Long gymId;

    @NotNull
    Long trainingId;

    @NotNull
    LocalDate date;

    @NotNull
    LocalTime start;

    @NotNull
    LocalTime end;
}