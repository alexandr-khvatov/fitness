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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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

    @Test
    void findById() {
        var gym = getGym();
        GymReadDto gymDto = getGymReadDto();
        doReturn(gymDto).when(gymReadDtoMapper).toDto(gym);
        doReturn(Optional.of(gym)).when(gymRepository).findById(anyLong());

        Optional<GymReadDto> actualResult = gymService.findById(anyLong());

        assertThat(actualResult).isNotNull().isEqualTo(Optional.of(gymDto));
        verify(gymRepository).findById(anyLong());
    }

    @Test
    void create() {
        var gym = getGym();
        var gymCreateEditDto = getGymCreateEditDto();
        doReturn(gym).when(gymCreateEditDtoMapper).toEntity(gymCreateEditDto);
        var savedGym = getGymWithId();
        doReturn(savedGym).when(gymRepository).save(gym);
        GymReadDto gymDto = getGymReadDto();
        doReturn(gymDto).when(gymReadDtoMapper).toDto(savedGym);

        GymReadDto actualResult = gymService.create(gymCreateEditDto);

        assertThat(actualResult).isNotNull().isEqualTo(gymDto);
        verify(gymRepository).save(gym);
    }

    private Gym getGym() {
        return new Gym();
    }

    private Gym getGymWithId() {
        Gym gym = new Gym();
        gym.setId(1111L);
        return gym;
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
                .id(111L)
                .name("Gym")
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
                .name("Gym")
                .address("City")
                .phone("88005553535")
                .email("test@email.com")
                .vkLink("vk.com")
                .tgLink("tg.com")
                .instLink("inst.com")
                .build();
    }
}