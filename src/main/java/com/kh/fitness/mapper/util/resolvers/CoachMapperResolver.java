package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public class CoachMapperResolver extends EntityByJpaRepositoryMapperResolver<Coach, Long> {
    public CoachMapperResolver(JpaRepository<Coach, Long> repository) {
        super(repository);
    }
}
