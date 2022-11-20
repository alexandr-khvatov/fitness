package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramReadWithSubProgramsDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.mapper.subTrainingProgram.SubProgramReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgramReadWithSubProgramsMapper implements Mapper<TrainingProgram, ProgramReadWithSubProgramsDto> {
    private final SubProgramReadMapper subProgramReadMapper;

    public ProgramReadWithSubProgramsDto map(TrainingProgram f) {
        return ProgramReadWithSubProgramsDto.builder()
                .id(f.getId())
                .name(f.getName())
                .overview(f.getOverview())
                .image(f.getImage() != null ? "http://localhost:8080/api/v1/programs/" + f.getId() + "/avatar" : null)
                .description(f.getDescription())
                .subTrainings(f.getSubTrainingPrograms().stream()
                        .map(subProgramReadMapper::map)
                        .toList())
                .build();
    }
}
