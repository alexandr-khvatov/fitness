package com.kh.fitness.api;

import com.kh.fitness.entity.Coach;
import com.kh.fitness.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequestMapping(API_V1 + "/coaches")
@RequiredArgsConstructor
public class CoachController {
    private final CoachService coachService;

    @GetMapping("/{id}")
    public Optional<Coach> findById(@PathVariable Long id) {
        return coachService.findById(id);
    }

    @PostMapping
    public Coach create(@RequestBody Coach coach) {
        return coachService.create(coach);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(coachService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}