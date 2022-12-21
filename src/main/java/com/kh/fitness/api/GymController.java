package com.kh.fitness.api;

import com.kh.fitness.dto.GymHours;
import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequestMapping(API_V1 + "/gyms")
@RequiredArgsConstructor
public class GymController {
    private final GymService gymService;

    @GetMapping
    public List<Gym> findAll() {
        return gymService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<GymReadDto> findById(@PathVariable Long id) {
        return gymService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GymReadDto create(@RequestBody GymCreateEditDto gym, Authentication authentication) {
        return gymService.create(gym);
    }

    @PutMapping("/{id}")
    public Optional<GymReadDto> update(@PathVariable Long id, @RequestBody GymCreateEditDto gym) {
        return gymService.update(id, gym);
    }

    @PutMapping("/{id}/open_hours")
    public Optional<GymReadDto> updateOpeningHours(@PathVariable Long id, @RequestBody GymHours openingHours) {
        return gymService.updateOpeningHours(id,openingHours);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!gymService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}