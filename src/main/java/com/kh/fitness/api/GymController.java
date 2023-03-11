package com.kh.fitness.api;

import com.kh.fitness.dto.PageResponse;
import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.dto.gym.GymFilter;
import com.kh.fitness.dto.gym.GymHours;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.service.GymServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequestMapping(API_V1 + "/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymServiceImpl gymService;

    @GetMapping
    public PageResponse<GymReadDto> findAllByFilter(GymFilter filter, Pageable pageable) {
        return PageResponse.of(gymService.findAllByFilter(filter, pageable));
    }

    @GetMapping("/{id}")
    public Optional<GymReadDto> findById(@PathVariable Long id) {
        return gymService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GymReadDto create(@RequestBody GymCreateEditDto gym) {
        return gymService.create(gym);
    }

    @PutMapping("/{id}")
    public Optional<GymReadDto> update(@PathVariable Long id, @RequestBody GymCreateEditDto gym) {
        return gymService.update(id, gym);
    }

    @PutMapping("/{id}/hours")
    public Optional<GymReadDto> updateOpeningHours(@PathVariable Long id, @RequestBody GymHours openingHours) {
        return gymService.updateWorkingHours(id, openingHours);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!gymService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}