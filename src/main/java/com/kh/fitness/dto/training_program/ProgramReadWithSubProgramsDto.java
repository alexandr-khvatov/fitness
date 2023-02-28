package com.kh.fitness.dto.training_program;

import com.kh.fitness.dto.subtraining_program.SubProgramReadDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramReadWithSubProgramsDto {
    private Long id;
    private String name;
    private String overview;
    private String image;
    private String description;
    private List<SubProgramReadDto> subTrainings;
}
