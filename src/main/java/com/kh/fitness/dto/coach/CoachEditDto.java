package com.kh.fitness.dto.coach;

import com.kh.fitness.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachEditDto {
    @NotBlank
    private String firstname;

    @NotBlank
    private String patronymic;

    @NotBlank
    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Phone
    private String phone;

    @Email
    private String email;

    @NotBlank
    private String specialization;

    private String description;

    private Long gymId;
}