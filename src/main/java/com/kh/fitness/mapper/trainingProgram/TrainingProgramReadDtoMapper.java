package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.TrainingProgramReadDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.mapper.subTrainingProgram.SubTrainingProgramReadDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingProgramReadDtoMapper implements Mapper<TrainingProgram, TrainingProgramReadDto> {
    private final SubTrainingProgramReadDtoMapper subTrainingProgramReadDtoMapper;

    @Override
    public TrainingProgramReadDto map(TrainingProgram f) {
        return TrainingProgramReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .overview(f.getOverview())
                .image(f.getImage())
                .description(f.getDescription())
                .subTrainings(f.getSubTrainingPrograms().stream()
                        .map(subTrainingProgramReadDtoMapper::map)
                        .toList())
                .build();
    }
}
