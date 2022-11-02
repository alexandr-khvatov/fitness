package com.kh.fitness.service;

import com.kh.fitness.dto.FreePassRequestCreateDto;
import com.kh.fitness.entity.FreePassRequest;
import com.kh.fitness.mapper.FreePassRequestCreateDtoMapper;
import com.kh.fitness.repository.FreePassRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreePassRequestService {
    private final FreePassRequestRepository freePassRequestRepository;
    private final FreePassRequestCreateDtoMapper freePassRequestCreateDtoMapper;

    public Optional<FreePassRequest> findById(Long id) {
        return freePassRequestRepository.findById(id);
    }

    public FreePassRequestCreateDto create(FreePassRequestCreateDto dto) {
        return Optional.of(dto)
                .map(freePassRequestCreateDtoMapper::map)
                .map(freePassRequestRepository::saveAndFlush)
                .map(freePassRequestCreateDtoMapper::map)
                .orElseThrow();
    }

    public Boolean delete(Long id) {
        return freePassRequestRepository.findById(id)
                .map(entity -> {
                    freePassRequestRepository.delete(entity);
                    freePassRequestRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
