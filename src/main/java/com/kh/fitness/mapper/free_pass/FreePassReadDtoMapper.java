package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassEditDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.training.TrainingReadMapper;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.TrainingMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {GymMapperResolver.class, TrainingMapperResolver.class, TrainingReadMapper.class})
public interface FreePassReadDtoMapper {
    @Mapping(target = "start", ignore = true)
    @Mapping(target = "end", ignore = true)
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "trainingId", source = "training.id")
    @Mapping(target = "training", source = "training")
    @Mapping(target = "trainingName", source = "trainingName")
    FreePassReadDto toDto(FreePass s);

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
    FreePass toEntity(FreePassEditDto s);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "trainingName", ignore = true)
    @Mapping(target = "training", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "lastname", ignore = true)
    @Mapping(target = "isDone", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gym", ignore = true)
    @Mapping(target = "firstname", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @InheritConfiguration
    FreePass updateEntity(FreePassEditDto s, @MappingTarget FreePass t);
}