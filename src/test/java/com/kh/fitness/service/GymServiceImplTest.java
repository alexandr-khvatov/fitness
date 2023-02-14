package com.kh.fitness.service;

import com.kh.fitness.dto.gym.GymCreateEditDto;
import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import com.kh.fitness.dto.gym.GymReadDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.mapper.gym.GymCreateEditDtoMapper;
import com.kh.fitness.mapper.gym.GymReadDtoMapper;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GymServiceImplTest {
    @Mock
    private GymRepository gymRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private GymCreateEditDtoMapper gymCreateEditDtoMapper;
    @Mock
    private GymReadDtoMapper gymReadDtoMapper;
    @InjectMocks
    private GymServiceImpl gymService;

    private static final Long GYM_ID = 1L;

    @Test
    void findById_shouldReturnGym_whenExist() {
        var gym = getGymWithId();
        GymReadDto gymDto = getGymReadDto();

        doReturn(gymDto).when(gymReadDtoMapper).toDto(gym);
        doReturn(Optional.of(gym)).when(gymRepository).findById(GYM_ID);
        Optional<GymReadDto> actualResult = gymService.findById(GYM_ID);

        assertThat(actualResult).isPresent().isEqualTo(Optional.of(gymDto));
        verify(gymRepository).findById(any());
        verify(gymReadDtoMapper).toDto(any());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        doReturn(Optional.empty()).when(gymRepository).findById(anyLong());
        Optional<GymReadDto> actualResult = gymService.findById(anyLong());

        assertThat(actualResult).isEmpty();
        verify(gymRepository, times(1)).findById(any());
        verify(gymReadDtoMapper, times(0)).toDto(any());
    }

    @Test
    void create() {
        var gym = getGymWithoutId();
        var gymCreateEditDto = getGymCreateEditDto();
        var savedGym = getGymWithId();
        var gymDto = getGymReadDto();

        doReturn(gym).when(gymCreateEditDtoMapper).toEntity(gymCreateEditDto);
        doReturn(savedGym).when(gymRepository).save(gym);
        doReturn(gymDto).when(gymReadDtoMapper).toDto(savedGym);
        GymReadDto actualResult = gymService.create(gymCreateEditDto);

        assertThat(actualResult).isNotNull().isEqualTo(gymDto);
        verify(gymCreateEditDtoMapper).toEntity(any());
        verify(gymRepository).save(any());
        verify(gymReadDtoMapper).toDto(any());
    }

    private Gym getGymWithoutId() {
        return Gym.builder()
                .id(null)
                .name("GYM Java without id")
                .build();
    }

    private Gym getGymWithId() {
        return Gym.builder()
                .id(GYM_ID)
                .name("GYM Java with id")
                .build();
    }

    private GymReadDto getGymReadDto() {
        LocalTime stub = LocalTime.now();
        List<GymOpeningHourInfoDto> stubOpeningHours = List.of(GymOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(stub)
                .endTime(stub)
                .isOpen(true)
                .build());
        return GymReadDto.builder()
                .id(GYM_ID)
                .name("GYM Java with id")
                .address("City")
                .phone("88005553535")
                .email("test@email.com")
                .vkLink("vk.com")
                .tgLink("tg.com")
                .instLink("inst.com")
                .workingHoursOnWeekdays(stub.toString())
                .workingHoursOnWeekends(stub.toString())
                .minStartTime(stub)
                .maxEndTime(stub)
                .openingHours(stubOpeningHours)
                .build();
    }

    private GymCreateEditDto getGymCreateEditDto() {
        return GymCreateEditDto.builder()
                .name("GYM Java without id")
                .address("City")
                .phone("88005553535")
                .email("test@email.com")
                .vkLink("vk.com")
                .tgLink("tg.com")
                .instLink("inst.com")
                .build();
    }
}