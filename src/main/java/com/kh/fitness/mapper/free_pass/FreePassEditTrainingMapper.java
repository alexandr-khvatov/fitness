package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassEditTrainingDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.util.resolvers.TrainingMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {TrainingMapperResolver.class})
public interface FreePassEditTrainingMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstname", ignore = true)
    @Mapping(target = "lastname", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "trainingName", ignore = true)
    @Mapping(target = "gym", ignore = true)
    @Mapping(target = "isDone", constant = "false")
    @Mapping(target = "training", source = "trainingId")
    FreePass toEntity(FreePassEditTrainingDto s);

    @InheritConfiguration
    @Mapping(target = "isDone",ignore = true)
    FreePass updateEntity(FreePassEditTrainingDto s, @MappingTarget FreePass t);
}