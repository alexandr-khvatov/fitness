package com.kh.fitness.mapper.subTrainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubProgramReadDto;
import com.kh.fitness.entity.SubTrainingProgram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubProgramReadMapper {
    @Mapping(target = "programId", source = "trainingProgram.id")
    @Mapping(target = "image", expression = "java(imageUrl(s))")
    SubProgramReadDto map(SubTrainingProgram s);

    default String imageUrl(SubTrainingProgram s) {
        return s.getImage() != null ? "http://localhost:8080/api/v1/sub-programs/" + s.getId() + "/avatar" : null;
    }
}