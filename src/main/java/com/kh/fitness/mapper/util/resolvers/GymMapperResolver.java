package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public class GymMapperResolver extends EntityByJpaRepositoryMapperResolver<Gym, Long> {
    public GymMapperResolver(JpaRepository<Gym, Long> repository) {
        super(repository);
    }
}
