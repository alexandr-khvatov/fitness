package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.entity.Room;
import org.mapstruct.Mapper;

@Mapper
public interface RoomReadMapper {
    RoomReadDto map(Room s);
}
//@Component
//public class RoomReadMapper implements Mapper<Room, RoomReadDto> {
//    @Override
//    public RoomReadDto map(Room f) {
//        return RoomReadDto.builder()
//                .id(f.getId())
//                .name(f.getName())
//                .build();
//    }
//}
