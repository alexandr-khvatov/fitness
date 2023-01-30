package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.Room;
import com.kh.fitness.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public class RoomMapperResolver extends EntityByJpaRepositoryMapperResolver<Room, Long> {
    public RoomMapperResolver(JpaRepository<Room, Long> repository) {
        super(repository);
    }
}
