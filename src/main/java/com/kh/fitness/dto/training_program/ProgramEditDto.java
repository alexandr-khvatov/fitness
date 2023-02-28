package com.kh.fitness.dto.training_program;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramEditDto {
    @NotBlank
    private String name;

    @NotBlank
    private String overview;

    private String description;

    private Long gymId;
}
