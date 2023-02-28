package com.kh.fitness.mapper.subtraining_program;

import com.kh.fitness.dto.subtraining_program.SubProgramEditDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.mapper.util.resolvers.TrainingProgramMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {TrainingProgramMapperResolver.class})
public interface SubProgramEditMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "trainingProgram", source = "programId")
    SubTrainingProgram toEntity(SubProgramEditDto s);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainingProgram", ignore = true)
    @InheritConfiguration
    SubTrainingProgram updateEntity(SubProgramEditDto s, @MappingTarget SubTrainingProgram t);

}