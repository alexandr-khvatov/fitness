package com.kh.fitness.dto.trainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubProgramReadDto;
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
