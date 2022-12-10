package com.kh.fitness.mapper.room;

import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.Room;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomCreateMapper implements Mapper<RoomCreateDto, Room> {
    private final GymRepository gymRepository;
    public Room map(RoomCreateDto f) {
        var room = new Room();
        copy(f, room);
        return room;
    }

    private void copy(RoomCreateDto f, Room t) {
        t.setName(f.getName());
        t.setGym(getGym(f.getGymId()));
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
