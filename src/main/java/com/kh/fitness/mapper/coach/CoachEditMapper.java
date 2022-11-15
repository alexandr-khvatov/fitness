package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CoachEditMapper implements Mapper<CoachEditDto, Coach> {
    private final GymRepository gymRepository;

    @Override
    public Coach map(CoachEditDto f) {
        var coach = new Coach();
        coach.setFirstname(f.getFirstname());
        coach.setPatronymic(f.getPatronymic());
        coach.setLastname(f.getLastname());
        coach.setBirthDate(f.getBirthDate());
        coach.setPhone(f.getPhone());
        coach.setEmail(f.getEmail());
        coach.setSpecialization(f.getSpecialization());
        coach.setDescription(f.getDescription());
        coach.setGym(getGym(f.getGymId()));
        return coach;
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
