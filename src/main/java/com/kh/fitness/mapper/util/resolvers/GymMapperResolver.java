package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GymMapperResolver extends EntityByJpaRepositoryMapperResolver<Gym, Long> {
    public GymMapperResolver(JpaRepository<Gym, Long> repository) {
        super(repository);
    }
}
