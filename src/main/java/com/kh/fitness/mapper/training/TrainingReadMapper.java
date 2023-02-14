package com.kh.fitness.mapper.training;

import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TrainingReadMapper {

    @Mapping(target = "title",ignore = true)
    @Mapping(target = "start",ignore = true)
    @Mapping(target = "end",ignore = true)
    @Mapping(target = "dayOfWeek", expression = "java(s.getDayOfWeek().getValue())")
    @Mapping(target = "coachId", source = "coach.id")
    @Mapping(target = "coach", source = "coach.firstname")
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "room", source = "room.name")
    @Mapping(target = "subProgramId", source = "subTrainingProgram.id")
    @Mapping(target = "subProgram", source = "subTrainingProgram.name")
    @Mapping(target = "programId", source = "subTrainingProgram.trainingProgram.id")
    TrainingReadDto toDto(Training s);
}