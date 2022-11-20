package com.kh.fitness.dto.trainingProgram;

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
