package com.kh.fitness.mapper.subTrainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubProgramReadDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SubProgramReadMapper implements Mapper<SubTrainingProgram, SubProgramReadDto> {
    @Override
    public SubProgramReadDto map(SubTrainingProgram f) {
        return SubProgramReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .overview(f.getOverview())
                .image(f.getImage() != null ? "http://localhost:8080/api/v1/sub-programs/" + f.getId() + "/avatar" : null)
                .description(f.getDescription())
                .build();
    }
}
