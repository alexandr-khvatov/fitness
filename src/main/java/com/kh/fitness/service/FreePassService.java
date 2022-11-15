package com.kh.fitness.service;

import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.dto.free_pass.FreePassRequestCreateDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.free_pass.FreePassReadDtoMapper;
import com.kh.fitness.mapper.free_pass.FreePassRequestCreateDtoMapper;
import com.kh.fitness.repository.FreePassRepository;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreePassService {
    private final FreePassRepository freePassRepository;
    private final GymRepository gymRepository;
    private final FreePassReadDtoMapper freePassReadDtoMapper;
    private final FreePassRequestCreateDtoMapper freePassRequestCreateDtoMapper;

    public Optional<FreePass> findById(Long id) {
        return freePassRepository.findById(id);
    }

    public List<FreePassReadDto> findAllByGymId(Long gymId) {
        return freePassRepository.findAllByGymIdOrderByIsDone(gymId).stream()
                .map(freePassReadDtoMapper::map).toList();
    }

    @Transactional
    public FreePassReadDto create(Long gymId, FreePassRequestCreateDto dto) {
        var mayBeGym = gymRepository.findById(gymId).orElseThrow(() -> new EntityNotFoundException("Entity Gym with id: " + gymId));
        return Optional.of(dto)
                .map(freePassRequestCreateDtoMapper::map)
                .map(freePass -> {
                    freePass.setGym(mayBeGym);
                    return freePass;
                })
                .map(freePassRepository::saveAndFlush)
                .map(freePassReadDtoMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<FreePassReadDto> updateFieldIsDone(Long id) {
        return freePassRepository.findById(id)
                .map(entity -> {
                    entity.setIsDone(!entity.getIsDone());
                    return entity;
                })
                .map(freePassRepository::saveAndFlush)
                .map(freePassReadDtoMapper::map);
    }

    @Transactional
    public Boolean delete(Long id) {
        return freePassRepository.findById(id)
                .map(entity -> {
                    freePassRepository.delete(entity);
                    freePassRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
