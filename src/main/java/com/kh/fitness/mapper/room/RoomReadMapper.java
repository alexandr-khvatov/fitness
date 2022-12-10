package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.entity.Room;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RoomReadMapper implements Mapper<Room, RoomReadDto> {
    @Override
    public RoomReadDto map(Room f) {
        return RoomReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .build();
    }
}
