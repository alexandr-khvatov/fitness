package com.kh.fitness.dto.subTrainingProgram;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubProgramReadDto {
    private Long id;
    private String name;
    private String overview;
    private String image;
    private Long programId;
    private String description;
}
