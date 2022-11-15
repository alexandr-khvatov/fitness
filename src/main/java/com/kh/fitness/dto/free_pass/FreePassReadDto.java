package com.kh.fitness.dto.free_pass;

import com.kh.fitness.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreePassReadDto {
    Long id;

    @NotBlank
    String firstname;

    String lastname;

    @Phone
    String phone;

    @Email
    String email;

    Boolean isDone;
}

