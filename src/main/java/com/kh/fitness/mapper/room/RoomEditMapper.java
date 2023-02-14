package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomEditDto;
import com.kh.fitness.entity.Room;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {GymMapperResolver.class})
public interface RoomEditMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "gym",source = "gymId")
    Room updateEntity(RoomEditDto roomEditDto,@MappingTarget Room room);
}