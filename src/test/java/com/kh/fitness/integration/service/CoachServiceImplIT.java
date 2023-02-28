package com.kh.fitness.integration.service;

import com.kh.fitness.integration.IntegrationTestBase;
import com.kh.fitness.service.CoachServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CoachServiceImplIT extends IntegrationTestBase {

    private final CoachServiceImpl coachService;

    public static final Long COACH_ID = 1L;
    public static final Long COACH_ID_NOT_EXIST_IN_DB = -128L;

    @Test
    void findById_shouldFindCoach_whenExist() {
        var actualResult = coachService.findById(COACH_ID);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(COACH_ID);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenNotExist() {
        var actualResult = coachService.findById(COACH_ID_NOT_EXIST_IN_DB);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void delete_shouldReturnTrue_whenCoachRemoved() {
        assertThat(coachService.delete(COACH_ID)).isTrue();
    }

    @Test
    void delete_shouldReturnFalse_whenCoachNotExist() {
        assertThat(coachService.delete(COACH_ID_NOT_EXIST_IN_DB)).isFalse();
    }
}