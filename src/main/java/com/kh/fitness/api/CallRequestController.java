package com.kh.fitness.api;

import com.kh.fitness.dto.CallRequestCreateDto;
import com.kh.fitness.entity.CallRequest;
import com.kh.fitness.service.CallRequestService;
import com.kh.fitness.api.util.PathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(PathUtils.API_V1 + "/call-request")
@RequiredArgsConstructor
public class CallRequestController {
    private final CallRequestService callRequestService;

    @GetMapping("/{id}")
    public Optional<CallRequest> findById(@PathVariable Long id) {
        return callRequestService.findById(id);
    }

    @PostMapping
    public CallRequest create(@RequestBody CallRequestCreateDto dto) {
        return callRequestService.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(callRequestService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}