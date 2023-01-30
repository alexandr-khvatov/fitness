package com.kh.fitness.mapper.util.resolvers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public abstract class EntityByJpaRepositoryMapperResolver<T, ID> {
    private final JpaRepository<T, ID> repository;

    public T resolve(@NotNull ID id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("Entity with id:{ " + id + " } notfound");
        });
    }
}
