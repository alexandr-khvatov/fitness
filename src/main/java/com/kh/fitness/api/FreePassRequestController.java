package com.kh.fitness.api;

import com.kh.fitness.dto.FreePassRequestCreateDto;
import com.kh.fitness.entity.FreePassRequest;
import com.kh.fitness.service.FreePassRequestService;
import com.kh.fitness.api.util.PathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(PathUtils.API_V1 + "/free-pass")
@RequiredArgsConstructor
public class FreePassRequestController {
    private final FreePassRequestService freePassRequestService;

    @GetMapping("/{id}")
    public Optional<FreePassRequest> findById(@PathVariable Long id) {
        return freePassRequestService.findById(id);
    }

    @PostMapping
    public FreePassRequestCreateDto create(@RequestBody FreePassRequestCreateDto dto) {
        return freePassRequestService.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(freePassRequestService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}