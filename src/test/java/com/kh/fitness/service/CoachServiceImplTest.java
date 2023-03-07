package com.kh.fitness.service;

import com.kh.fitness.mapper.coach.CoachCreateMapper;
import com.kh.fitness.mapper.coach.CoachEditMapper;
import com.kh.fitness.mapper.coach.CoachReadDtoMapper;
import com.kh.fitness.repository.CoachRepository;
import com.kh.fitness.service.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoachServiceImplTest {

    @Mock
    private ImageService imageService;
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private CoachEditMapper coachEditMapper;
    @Mock
    private CoachCreateMapper coachCreateMapper;
    @Mock
    private CoachReadDtoMapper coachReadDtoMapper;
    @InjectMocks
    CoachServiceImpl coachService;

    private static final Long COACH_ID = 1L;

/*
    @Test
    void findById_shouldReturnCoach_whenExist() {
        var coach = getCoachWithId();
        CoachReadDto coachDto = getCoachReadDto();

        doReturn(coachDto).when(coachReadDtoMapper).toDto(coach);
        doReturn(Optional.of(coach)).when(coachRepository).findById(COACH_ID);
        Optional<CoachReadDto> actualResult = coachService.findById(COACH_ID);

        assertThat(actualResult).isPresent().isEqualTo(Optional.of(coachDto));
        verify(coachRepository).findById(any());
        verify(coachReadDtoMapper).toDto(any());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        doReturn(Optional.empty()).when(coachRepository).findById(anyLong());
        Optional<CoachReadDto> actualResult = coachService.findById(anyLong());

        assertThat(actualResult).isEmpty();
        verify(coachRepository, times(1)).findById(any());
        verify(coachReadDtoMapper, times(0)).toDto(any());
    }
*/

    @Test
    void findAvatar() {
    }

/*    @Test
    void create() {
        var coach = getCoachWithoutId();
        var coachCreateEditDto = getCoachCreateEditDto();
        var savedCoach = getCoachWithId();
        var coachDto = getCoachReadDto();

        doReturn(coach).when(coachCreateEditDtoMapper).toEntity(coachCreateEditDto);
        doReturn(savedCoach).when(coachRepository).save(coach);
        doReturn(coachDto).when(coachReadDtoMapper).toDto(savedCoach);
        CoachReadDto actualResult = coachService.create(coachCreateEditDto);

        assertThat(actualResult).isNotNull().isEqualTo(coachDto);
        verify(coachCreateEditDtoMapper).toEntity(any());
        verify(coachRepository).save(any());
        verify(coachReadDtoMapper).toDto(any());
    }

    @Test
    void update() {
    }

    @Test
    void updateAvatar() {
    }

    @Test
    void removeAvatar() {
    }

    @Test
    void delete() {
    }


    private Coach getCoachWithoutId() {
        return Coach.builder()
                .id(null)
                .firstname("Ivan")
                .firstname("Ivanovich")
                .firstname("Ivanov")
                .build();
    }

    private Coach getCoachWithId() {
        return Coach.builder()
                .id(COACH_ID)
                .name("GYM Java with id")
                .build();
    }

    private CoachReadDto getCoachReadDto() {
        LocalTime stub = LocalTime.now();
        List<CoachOpeningHourInfoDto> stubOpeningHours = List.of(CoachOpeningHourInfoDto.builder()
                .dayOfWeek(1)
                .startTime(stub)
                .endTime(stub)
                .isOpen(true)
                .build());
        return CoachReadDto.builder()
                .id(COACH_ID)
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

    private CoachCreateEditDto getCoachCreateEditDto() {
        return CoachCreateEditDto.builder()
                .name("GYM Java without id")
                .address("City")
                .phone("88005553535")
                .email("test@email.com")
                .vkLink("vk.com")
                .tgLink("tg.com")
                .instLink("inst.com")
                .build();
    }*/
}