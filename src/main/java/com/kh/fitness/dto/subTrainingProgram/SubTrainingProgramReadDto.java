package com.kh.fitness.dto.subTrainingProgram;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubTrainingProgramReadDto {
    private Long id;
    private String name;
    private String overview;
    private String image;
    private String description;
}
