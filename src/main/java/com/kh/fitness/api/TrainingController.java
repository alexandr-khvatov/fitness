package com.kh.fitness.api;

import com.kh.fitness.dto.training.TrainingCreateDto;
import com.kh.fitness.dto.training.TrainingEditDto;
import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(API_V1 + "/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping("/{id}")
    public Optional<TrainingReadDto> findById(@PathVariable Long id) {
        return trainingService.findById(id);
    }

    @GetMapping
    public List<TrainingReadDto> findAllByGymId(@RequestParam Long gymId) {
        return trainingService.findAllByGymId(gymId);
    }

    @PostMapping
    public TrainingReadDto create(@RequestBody TrainingCreateDto training) {
        return trainingService.create(training);
    }

    @PutMapping("/{id}")
    @ResponseStatus(CREATED)
    public Optional<TrainingReadDto> update(@PathVariable Long id, @RequestBody TrainingEditDto training) {
        return trainingService.update(id, training);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(trainingService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}