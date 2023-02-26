package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoomReadMapper {
    @Mapping(target = "gymId",source = "gym.id")
    RoomReadDto toDto(Room s);
}