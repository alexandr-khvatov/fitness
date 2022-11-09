package com.kh.fitness.mapper.subTrainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubTrainingProgramReadDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SubTrainingProgramReadDtoMapper implements Mapper<SubTrainingProgram, SubTrainingProgramReadDto> {
    @Override
    public SubTrainingProgramReadDto map(SubTrainingProgram f) {
        return SubTrainingProgramReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .overview(f.getOverview())
                .image(f.getImage())
                .description(f.getDescription())
                .build();
    }
}
