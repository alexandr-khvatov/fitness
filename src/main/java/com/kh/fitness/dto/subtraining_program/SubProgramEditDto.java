package com.kh.fitness.dto.subtraining_program;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubProgramEditDto {
    @NotBlank
    private String name;

    @NotBlank
    private String overview;

    private String description;

    private Long programId;
}
