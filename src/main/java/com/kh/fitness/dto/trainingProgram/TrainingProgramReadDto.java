package com.kh.fitness.dto.trainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubTrainingProgramReadDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramReadDto {
    private Long id;
    private String name;
    private String overview;
    private String image;
    private String description;
    private List<SubTrainingProgramReadDto> subTrainings;
}
