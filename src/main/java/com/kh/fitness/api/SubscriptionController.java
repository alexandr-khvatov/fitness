package com.kh.fitness.api;

import com.kh.fitness.entity.subscription.Subscription;
import com.kh.fitness.service.SubscriptionServiceImpl;
import com.kh.fitness.api.util.PathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(PathUtils.API_V1 + "/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionServiceImpl coachService;

    @GetMapping("/{id}")
    public Optional<Subscription> findById(@PathVariable Long id) {
        return coachService.findById(id);
    }

    @PostMapping
    public Subscription create(@RequestBody Subscription subscription) {
        return coachService.create(subscription);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(coachService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}