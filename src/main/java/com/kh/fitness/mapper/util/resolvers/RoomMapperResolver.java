package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomMapperResolver extends EntityByJpaRepositoryMapperResolver<Room, Long> {
    public RoomMapperResolver(JpaRepository<Room, Long> repository) {
        super(repository);
    }
}
