package com.kh.fitness.service;

import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.mapper.coach.CoachReadDtoMapper;
import com.kh.fitness.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final CoachReadDtoMapper coachReadDtoMapper;

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    public List<CoachReadDto> findAllByGymId(Long gymId) {
        return coachRepository.findAllByGymId(gymId).stream()
                .map(coachReadDtoMapper::map).toList();
    }


    public Coach create(Coach coach) {
        return Optional.of(coach)
                .map(coachRepository::saveAndFlush)
                .orElseThrow();
    }

    public Boolean delete(Long id) {
        return coachRepository.findById(id)
                .map(entity -> {
                    coachRepository.delete(entity);
                    coachRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}