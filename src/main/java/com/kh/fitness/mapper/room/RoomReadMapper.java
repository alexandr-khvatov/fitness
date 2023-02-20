package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.entity.Room;
import org.mapstruct.Mapper;

@Mapper
public interface RoomReadMapper {
    RoomReadDto toDto(Room s);
}
