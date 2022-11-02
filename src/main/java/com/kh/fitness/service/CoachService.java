package com.kh.fitness.service;

import com.kh.fitness.entity.Coach;
import com.kh.fitness.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
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