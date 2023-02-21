package com.kh.fitness.service;

import com.kh.fitness.entity.Subscription;
import com.kh.fitness.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl {
    private final SubscriptionRepository subscriptionRepository;

    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription create(Subscription subscription) {
        return Optional.of(subscription)
                .map(subscriptionRepository::saveAndFlush)
                .orElseThrow();
    }

    public Boolean delete(Long id) {
        return subscriptionRepository.findById(id)
                .map(entity -> {
                    subscriptionRepository.delete(entity);
                    subscriptionRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}