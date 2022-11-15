package com.kh.fitness.mapper.coach;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.util.function.Predicate.not;

@RequiredArgsConstructor
@Component
public class CoachCreateMapper implements Mapper<CoachCreateDto, Coach> {
    private final GymRepository gymRepository;

    @Override
    public Coach map(CoachCreateDto f) {
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

        Optional.ofNullable(f.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> coach.setImage(image.getOriginalFilename()));

        return coach;
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
