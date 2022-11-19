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
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachCreateDto {
    @NotBlank
    private String firstname;

    @NotBlank
    private String patronymic;

    @NotBlank
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
    private String specialization;

    private String description;

    private Long gymId;

    MultipartFile image;
}