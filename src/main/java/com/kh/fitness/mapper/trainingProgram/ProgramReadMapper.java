package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramReadDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgramReadMapper implements Mapper<TrainingProgram, ProgramReadDto> {

    public ProgramReadDto map(TrainingProgram f) {
        return ProgramReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .overview(f.getOverview())
                .image(f.getImage() != null ? "http://localhost:8080/api/v1/programs/" + f.getId() + "/avatar" : null)
                .description(f.getDescription())
                .build();
    }
}
