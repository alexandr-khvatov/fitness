package com.kh.fitness.service;

import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.mapper.GymCreateEditDtoMapper;
import com.kh.fitness.mapper.GymReadDtoMapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class GymService {
    private final GymRepository gymRepository;

    private final GymCreateEditDtoMapper gymCreateEditDtoMapper;
    private final GymReadDtoMapper gymReadDtoMapper;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public GymReadDto create(@Valid GymCreateEditDto gym) {
        return Optional.of(gym)
                .map(gymCreateEditDtoMapper::map)
                .map(gymRepository::save)
                .map(gymReadDtoMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<Gym> update(Long id, GymCreateEditDto updateGym) {
        return gymRepository.findById(id)
                .map(entity -> gymCreateEditDtoMapper.map(updateGym, entity))
                .map(gymRepository::saveAndFlush);
    }

    @Transactional
    public boolean delete(Long id) {
        return gymRepository.findById(id)
                .map(entity -> {
                    gymRepository.delete(entity);
                    gymRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<GymReadDto> findById(Long id) {
        return gymRepository.findById(id)
                .map(gymReadDtoMapper::map);
    }

    public List<Gym> findAll() {
        return gymRepository.findAll();
    }

    public Optional<GymReadDto> findByName(String name) {
        return gymRepository.findByName(name)
                .map(gymReadDtoMapper::map);
    }
}