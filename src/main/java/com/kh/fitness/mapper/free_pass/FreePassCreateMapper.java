package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.TrainingMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {GymMapperResolver.class, TrainingMapperResolver.class})
public interface FreePassCreateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstname", ignore = true)
    @Mapping(target = "lastname", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "trainingName", ignore = true)
    @Mapping(target = "isDone", constant = "false")
    @Mapping(target = "gym", source = "gymId")
    @Mapping(target = "training", source = "trainingId")
    FreePass toEntity(FreePassCreateDto f);

    @InheritConfiguration
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "trainingId", source = "training.id")
    @Mapping(target = "start", source = "startTime")
    @Mapping(target = "end", source = "endTime")
    FreePassCreateDto toDto(FreePass from);
}