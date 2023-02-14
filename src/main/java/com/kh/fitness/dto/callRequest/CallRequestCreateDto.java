package com.kh.fitness.dto.callRequest;

import com.kh.fitness.validation.Phone;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CallRequestCreateDto {
    @NotBlank
    String firstname;

    String lastname;

    @Phone
    String phone;
}
