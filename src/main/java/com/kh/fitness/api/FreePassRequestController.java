package com.kh.fitness.api;

import com.kh.fitness.api.util.PathUtils;
import com.kh.fitness.dto.free_pass.FreePassEditTrainingDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.service.FreePassServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PathUtils.API_V1 + "/free-pass")
@RequiredArgsConstructor
public class FreePassRequestController {
    private final FreePassServiceImpl freePassRequestService;

    @GetMapping("/{id}")
    public Optional<FreePassReadDto> findById(@PathVariable Long id) {
        return freePassRequestService.findById(id);
    }

    @GetMapping
    public List<FreePassReadDto> findAllByGymId(@RequestParam Long gymId) {
        return freePassRequestService.findAllByGymId(gymId);
    }

    @PostMapping
    public FreePassReadDto create(@RequestBody FreePassCreateDto dto) {
        return freePassRequestService.create(dto);
    }

    @PutMapping("/{id}")
    public Optional<FreePassReadDto> update(@PathVariable Long id) {
        return freePassRequestService.updateFieldIsDone(id);
    }

    @PutMapping("/change/{id}")
    public Optional<FreePassReadDto> changeTraining(@PathVariable Long id,@RequestBody FreePassEditTrainingDto dto) {
        return freePassRequestService.changeTraining(id,dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(freePassRequestService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}