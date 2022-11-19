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

    public Coach map(CoachEditDto f,Coach t){
        t.setFirstname(f.getFirstname());
        t.setPatronymic(f.getPatronymic());
        t.setLastname(f.getLastname());
        t.setBirthDate(f.getBirthDate());
        t.setPhone(f.getPhone());
        t.setEmail(f.getEmail());
        t.setSpecialization(f.getSpecialization());
        t.setDescription(f.getDescription());
        t.setGym(getGym(f.getGymId()));
        return t;
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
