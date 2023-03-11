package com.kh.fitness.api;

import com.kh.fitness.api.util.PathUtils;
import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassEditDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.service.free_pass.FreePassServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping(PathUtils.API_V1 + "/free-pass")
@RequiredArgsConstructor
public class FreePassController {
    private final FreePassServiceImpl freePassRequestService;

    private static final String ERROR_MSG_NOT_FOUND = "Free pass with id %s not found";

    @GetMapping("/{id}")
    public FreePassReadDto findById(@PathVariable Long id) {
        return freePassRequestService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
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
    public FreePassReadDto update(@PathVariable Long id) {
        return freePassRequestService.updateFieldIsDone(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @PutMapping("/change/{id}")
    public FreePassReadDto changeTraining(@PathVariable Long id, @RequestBody FreePassEditDto dto) {
        return freePassRequestService.changeTraining(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(freePassRequestService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id));
        }
    }
}