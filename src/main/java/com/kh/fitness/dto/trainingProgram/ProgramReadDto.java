package com.kh.fitness.dto.trainingProgram;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramReadDto {
    private Long id;
    private String name;
    private String overview;
    private String image;
    private String description;
}
