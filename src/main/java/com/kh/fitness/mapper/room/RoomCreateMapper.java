package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.entity.Room;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {GymMapperResolver.class})
public interface RoomCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gym", source = "gymId")
    Room toEntity(RoomCreateDto s);
}