package com.kh.fitness.service;

import com.kh.fitness.dto.CallRequestCreateDto;
import com.kh.fitness.entity.CallRequest;
import com.kh.fitness.mapper.CallRequestCreateDtoMapper;
import com.kh.fitness.repository.CallRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallRequestService {
    private final CallRequestRepository callRequestRepository;
    private final CallRequestCreateDtoMapper callRequestCreateDtoMapper;

    public Optional<CallRequest> findById(Long id) {
        return callRequestRepository.findById(id);
    }

    public CallRequest create(CallRequestCreateDto requestFreePass) {
        return Optional.of(requestFreePass)
                .map(callRequestCreateDtoMapper::toEntity)
                .map(callRequestRepository::saveAndFlush)
                .orElseThrow();
    }

    public Boolean delete(Long id) {
        return callRequestRepository.findById(id)
                .map(entity -> {
                    callRequestRepository.delete(entity);
                    callRequestRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}