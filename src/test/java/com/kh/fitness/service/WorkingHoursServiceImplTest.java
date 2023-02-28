package com.kh.fitness.service;

import com.kh.fitness.dto.gym.GymHours;
import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.entity.gym.GymOpeningHourInfo;
import com.kh.fitness.mapper.gym.GymOpeningHourInfoDtoMapper;
import com.kh.fitness.service.working_hours.WorkingHoursServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkingHoursServiceImplTest {
    @Mock
    private GymOpeningHourInfoDtoMapper openingHourInfoDtoMapper;
    @InjectMocks
    private WorkingHoursServiceImpl workingHoursService;

    @Test
    void getOpeningHours() {
        var stubLocalTime = LocalTime.now();
        var expectedInvoke = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(stubLocalTime)
                .endTime(stubLocalTime)
                .isOpen(Boolean.TRUE)
                .build();
        GymHours gymHours = GymHours.builder()
                .openingHours(List.of(expectedInvoke, expectedInvoke, expectedInvoke, expectedInvoke, expectedInvoke))
                .build();
        var expectedStub = GymOpeningHourInfo.of(DayOfWeek.MONDAY, stubLocalTime, stubLocalTime, Boolean.FALSE);
        var expected = List.of(expectedStub, expectedStub, expectedStub, expectedStub, expectedStub);

        doReturn(expectedStub).when(openingHourInfoDtoMapper).toEntity(expectedInvoke);
        List<GymOpeningHourInfo> actual = workingHoursService.getOpeningHours(gymHours);

        assertEquals(expected, actual);
        assertThat(actual).isEqualTo(expected);
        verify(openingHourInfoDtoMapper, times(gymHours.getOpeningHours().size())).toEntity(expectedInvoke);
    }

    @ParameterizedTest(name = "#{index} - Earliest opening time at {1} ")
    @MethodSource("getArgumentsForEarliestOpeningTime")
    void getEarliestOpeningTime_shouldFindEarliestOpeningTime(GymHours openingHours, Optional<LocalTime> expected) {
        Optional<LocalTime> actual = workingHoursService.getEarliestOpeningTime(openingHours);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getArgumentsForEarliestOpeningTime() {
        var stubEarliestOpening = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(8, 0, 0))
                .endTime(LocalTime.of(22, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub2 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(9, 0, 0))
                .endTime(LocalTime.of(23, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub3 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(9, 0, 0))
                .endTime(LocalTime.of(22, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub4 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(10, 0, 0))
                .endTime(LocalTime.of(17, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub5 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(11, 0, 0))
                .endTime(LocalTime.of(21, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();

        GymHours gymHoursEarliestOpeningAt8hour = GymHours.builder()
                .openingHours(List.of(stubEarliestOpening, stub2, stub3, stub4, stub5))
                .build();
        var stubEarliestOpeningDisabled = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(8, 0, 0))
                .endTime(LocalTime.of(22, 0, 0))
                .isOpen(Boolean.FALSE)
                .build();
        GymHours gymHoursEarliestOpeningAt9hour = GymHours.builder()
                .openingHours(List.of(stubEarliestOpeningDisabled, stub2, stub3, stub4, stub5))
                .build();
        GymHours gymHoursEmpty = GymHours.builder().openingHours(Collections.emptyList()).build();

        return Stream.of(
                Arguments.of(gymHoursEarliestOpeningAt8hour, Optional.of(LocalTime.of(8, 0, 0))),
                Arguments.of(gymHoursEarliestOpeningAt9hour, Optional.of(LocalTime.of(9, 0, 0))),
                Arguments.of(gymHoursEmpty, Optional.<LocalTime>empty())
        );
    }

    @ParameterizedTest(name = "#{index} - Latest closing time at {1} ")
    @MethodSource("getArgumentsForLatestClosingTime")
    void getLatestClosingTime(GymHours openingHours, Optional<LocalTime> expected) {
        Optional<LocalTime> actual = workingHoursService.getLatestClosingTime(openingHours);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getArgumentsForLatestClosingTime() {
        var stubLatestClosing = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(8, 0, 0))
                .endTime(LocalTime.of(23, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub2 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(9, 0, 0))
                .endTime(LocalTime.of(22, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub3 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(9, 0, 0))
                .endTime(LocalTime.of(22, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub4 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(10, 0, 0))
                .endTime(LocalTime.of(17, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();
        var stub5 = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(11, 0, 0))
                .endTime(LocalTime.of(21, 0, 0))
                .isOpen(Boolean.TRUE)
                .build();

        GymHours gymHoursEarliestOpeningAt8hour = GymHours.builder()
                .openingHours(List.of(stubLatestClosing, stub2, stub3, stub4, stub5))
                .build();
        var stubEarliestOpeningDisabled = GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(LocalTime.of(8, 0, 0))
                .endTime(LocalTime.of(23, 0, 0))
                .isOpen(Boolean.FALSE)
                .build();
        GymHours gymHoursEarliestOpeningAt9hour = GymHours.builder()
                .openingHours(List.of(stubEarliestOpeningDisabled, stub2, stub3, stub4, stub5))
                .build();
        GymHours gymHoursEmpty = GymHours.builder().openingHours(Collections.emptyList()).build();

        return Stream.of(
                Arguments.of(gymHoursEarliestOpeningAt8hour, Optional.of(LocalTime.of(23, 0, 0))),
                Arguments.of(gymHoursEarliestOpeningAt9hour, Optional.of(LocalTime.of(22, 0, 0))),
                Arguments.of(gymHoursEmpty, Optional.<LocalTime>empty())
        );
    }
}