package com.kh.fitness.service;

import com.kh.fitness.entity.subscription.Subscription;
import com.kh.fitness.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionServiceImpl {
    private final SubscriptionRepository subscriptionRepository;

    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Transactional
    public Subscription create(Subscription subscription) {
        return Optional.of(subscription)
                .map(subscriptionRepository::saveAndFlush)
                .orElseThrow();
    }

    @Transactional
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