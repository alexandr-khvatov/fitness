package com.kh.fitness.dto.training_program;

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
