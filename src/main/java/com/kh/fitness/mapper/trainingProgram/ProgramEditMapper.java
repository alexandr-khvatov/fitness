package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramEditDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {GymMapperResolver.class})
public interface ProgramEditMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "subTrainingPrograms", ignore = true)
    @Mapping(target = "gym", source = "gymId")
    TrainingProgram toEntity(ProgramEditDto s);

    @InheritConfiguration
    TrainingProgram updateEntity(ProgramEditDto s, @MappingTarget TrainingProgram t);
}