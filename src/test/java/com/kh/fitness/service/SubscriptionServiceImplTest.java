package com.kh.fitness.service;

import com.kh.fitness.entity.Period;
import com.kh.fitness.entity.Subscription;
import com.kh.fitness.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private static final Long SUBSCRIPTION_ID = 1L;

    @Test
    void findById_shouldReturnSubscription_whenExist() {
        var subscription = getSubscriptionWithId();

        doReturn(Optional.of(subscription)).when(subscriptionRepository).findById(SUBSCRIPTION_ID);
        Optional<Subscription> actualResult = subscriptionService.findById(SUBSCRIPTION_ID);

        assertThat(actualResult).isPresent().contains(subscription);
        verify(subscriptionRepository).findById(any());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        doReturn(Optional.empty()).when(subscriptionRepository).findById(anyLong());
        Optional<Subscription> actualResult = subscriptionService.findById(anyLong());

        assertThat(actualResult).isEmpty();
        verify(subscriptionRepository, times(1)).findById(any());
    }

    @Test
    void create() {
        var subscription = getSubscriptionWithoutId();
        var savedSubscription = getSubscriptionWithId();

        doReturn(savedSubscription).when(subscriptionRepository).saveAndFlush(subscription);
        var actualResult = subscriptionService.create(subscription);

        assertThat(actualResult).isNotNull().isEqualTo(savedSubscription);
        verify(subscriptionRepository).saveAndFlush(any());
    }

    @Test
    void delete_shouldReturnTrue_whenSubscriptionExistAndDeletedSuccessfully() {
        var subscription = getSubscriptionWithId();

        doReturn(Optional.of(subscription)).when(subscriptionRepository).findById(anyLong());
        doNothing().when(subscriptionRepository).delete(subscription);
        var actualResult = subscriptionService.delete(anyLong());

        assertThat(actualResult).isTrue();
        verify(subscriptionRepository).findById(any());
        verify(subscriptionRepository).delete(any());
        verify(subscriptionRepository).flush();
    }

    @Test
    void delete_shouldReturnFalse_whenSubscriptionMissing() {
        doReturn(Optional.empty()).when(subscriptionRepository).findById(anyLong());
        var actualResult = subscriptionService.delete(anyLong());

        assertThat(actualResult).isFalse();
        verify(subscriptionRepository).findById(any());
        verify(subscriptionRepository, times(0)).delete(any());
        verify(subscriptionRepository, times(0)).flush();
    }

    private Subscription getSubscriptionWithId() {
        return Subscription.builder()
                .id(SUBSCRIPTION_ID)
                .name("SubscriptionName")
                .price(1000)
                .period(Period.YEAR)
                .build();
    }

    private Subscription getSubscriptionWithoutId() {
        return Subscription.builder()
                .id(null)
                .name("SubscriptionName")
                .price(1000)
                .period(Period.YEAR)
                .build();
    }
}