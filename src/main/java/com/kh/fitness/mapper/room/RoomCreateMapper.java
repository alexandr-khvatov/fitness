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

/*@Component
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
}*/
