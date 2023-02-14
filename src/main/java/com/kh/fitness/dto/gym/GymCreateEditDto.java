package com.kh.fitness.dto.gym;

import com.kh.fitness.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymCreateEditDto {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Phone
    private String phone;

    @Email
    private String email;

    @URL
    private String vkLink;

    @URL
    private String tgLink;

    @URL
    private String instLink;
}
