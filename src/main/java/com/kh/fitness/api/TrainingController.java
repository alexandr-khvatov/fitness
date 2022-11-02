package com.kh.fitness.api;

import com.kh.fitness.entity.Training;
import com.kh.fitness.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequestMapping(API_V1 + "/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService coachService;

    @GetMapping("/{id}")
    public Optional<Training> findById(@PathVariable Long id) {
        return coachService.findById(id);
    }

    @PostMapping
    public Training create(@RequestBody Training training) {
        return coachService.create(training);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(coachService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}