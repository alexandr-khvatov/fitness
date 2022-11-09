package com.kh.fitness.dto.user;

import com.kh.fitness.validation.EmailNotExist;
import com.kh.fitness.validation.Phone;
import com.kh.fitness.validation.PhoneNotExist;
import com.kh.fitness.validation.group.CheckNotExistAfter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotBlank
    String firstname;

    @NotBlank
    String patronymic;

    @NotBlank
    String lastname;

    @Phone
    @PhoneNotExist(groups = CheckNotExistAfter.class)
    String phone;

    @Email
    @EmailNotExist(groups = CheckNotExistAfter.class)
    String email;

//    MultipartFile image;

    @NotEmpty
    Set<Long> roles;
}