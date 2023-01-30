package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.dto.room.RoomEditDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.Room;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(uses = {GymMapperResolver.class})
public interface RoomEditMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "gym",source = "gymId")
    Room updateEntity(RoomEditDto roomEditDto,@MappingTarget Room room);
}

//@Component
//@RequiredArgsConstructor
//public class RoomEditMapper implements Mapper<RoomEditDto, Room> {
//    private final GymRepository gymRepository;
//
//    @Override
//    public Room map(RoomEditDto f) {
//        var room = new Room();
//        copy(f, room);
//        return room;
//    }
//
//    @Override
//    public Room map(RoomEditDto roomEditDto, Room room) {
//        copy(roomEditDto, room);
//        return room;
//    }
//
//    private void copy(RoomEditDto f, Room t) {
//        t.setName(f.getName());
//        t.setGym(getGym(f.getGymId()));
//    }
//
//    public Gym getGym(Long gymId) {
//        return Optional.ofNullable(gymId)
//                .flatMap(gymRepository::findById)
//                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
//    }
//}
