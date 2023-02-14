package com.kh.fitness.mapper.training;

import com.kh.fitness.dto.training.TrainingCreateDto;
import com.kh.fitness.dto.training.TrainingEditDto;
import com.kh.fitness.entity.Training;
import com.kh.fitness.mapper.util.resolvers.CoachMapperResolver;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.RoomMapperResolver;
import com.kh.fitness.mapper.util.resolvers.SubTrainingProgramMapperResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.DayOfWeek;

@Mapper(uses = {
        SubTrainingProgramMapperResolver.class,
        RoomMapperResolver.class,
        CoachMapperResolver.class,
        GymMapperResolver.class
})
public interface TrainingEditMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "subTrainingProgram", source = "subProgramId")
    @Mapping(target = "room", source = "roomId")
    @Mapping(target = "coach", source = "coachId")
    @Mapping(target = "gym", source = "gymId")
    @Mapping(target = "dayOfWeek", expression = "java(dayOfWeek(s.getDayOfWeek()))")
    Training toEntity(TrainingCreateDto s);


    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "subTrainingProgram", source = "subProgramId")
    @Mapping(target = "room", source = "roomId")
    @Mapping(target = "coach", source = "coachId")
    @Mapping(target = "gym", source = "gymId")
    Training updateEntity(TrainingEditDto f, @MappingTarget Training t);

    default DayOfWeek dayOfWeek(Integer day) {
        return DayOfWeek.of(day);
    }
}