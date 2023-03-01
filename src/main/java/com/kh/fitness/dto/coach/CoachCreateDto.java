package com.kh.fitness.dto.coach;

import com.kh.fitness.validation.EmailNotExist;
import com.kh.fitness.validation.Phone;
import com.kh.fitness.validation.PhoneNotExist;
import com.kh.fitness.validation.group.CheckNotExistAfter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachCreateDto {
    @NotBlank
    @NotNull
    private String firstname;

    @NotBlank
    @NotNull
    private String patronymic;

    @NotBlank
    @NotNull
    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Phone
    @PhoneNotExist(groups = CheckNotExistAfter.class)
    private String phone;

    @Email
    @EmailNotExist(groups = CheckNotExistAfter.class)
    private String email;

    @NotBlank
    @NotNull
    private String specialization;

    private String description;

    @NotNull
    private Long gymId;

    private MultipartFile image;
}