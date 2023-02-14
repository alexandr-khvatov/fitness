package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(uses = GymMapperResolver.class)
public interface CoachEditMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "trainings",ignore = true)
    @Mapping(target = "image",ignore = true)
    @Mapping(target = "gym",source = "gymId")
    Coach toEntity(CoachEditDto s);
    @InheritConfiguration
    Coach updateCoach(CoachEditDto s, @MappingTarget Coach t);
}
