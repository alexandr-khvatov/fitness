package com.kh.fitness.api;

import com.kh.fitness.dto.trainingProgram.TrainingProgramReadDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;

@RestController
@RequestMapping(API_V1 + "/programs")
@RequiredArgsConstructor
public class TrainingProgramController {
    private final TrainingProgramService trainingProgramService;

    @GetMapping("/{id}")
    public Optional<TrainingProgram> findById(@PathVariable Long id) {
        return trainingProgramService.findById(id);
    }

    @GetMapping
    public List<TrainingProgramReadDto> findAllByGymId(@RequestParam Long gymId) {
        return trainingProgramService.findAllByGymId(gymId);
    }

    @PostMapping
    public TrainingProgram create(@RequestBody TrainingProgram trainingProgram) {
        return trainingProgramService.create(trainingProgram);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(trainingProgramService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}