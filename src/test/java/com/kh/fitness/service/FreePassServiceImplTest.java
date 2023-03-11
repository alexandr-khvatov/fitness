package com.kh.fitness.service;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.Training;
import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.mapper.free_pass.FreePassCreateMapper;
import com.kh.fitness.mapper.free_pass.FreePassEditTrainingMapper;
import com.kh.fitness.mapper.free_pass.FreePassReadDtoMapper;
import com.kh.fitness.repository.FreePassRepository;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.TrainingRepository;
import com.kh.fitness.service.email.EmailService;
import com.kh.fitness.service.free_pass.FreePassServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class FreePassServiceImplTest {
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingServiceImpl trainingServiceImpl;
    @Mock
    private FreePassRepository freePassRepository;
    @Mock
    private GymRepository gymRepository;
    @Mock
    private FreePassReadDtoMapper freePassReadDtoMapper;
    @Mock
    private FreePassCreateMapper freePassCreateMapper;
    @Mock
    private FreePassEditTrainingMapper freePassEditTrainingMapper;
    @Mock
    private EmailService emailService;
    @InjectMocks
    FreePassServiceImpl freePassServiceImpl;
    public static final Long GYM_ID = 1L;
    public static final Long GYM_ID_NOT_EXIST = -128L;
    public static final Long TRAINING_ID = 1L;
    public static final Long TRAINING_ID_NOT_EXIST = -128L;
    public static final LocalDate date = LocalDate.now();
    public static final String ERROR_MSG_NOT_FOUND = "Free pass with id %s not found";

    @Test
    void create() {
        var gym = Gym.builder().id(GYM_ID).build();
        var startTime = LocalTime.of(9, 0);
        var endTime = LocalTime.of(10, 0);
        var training = Training.builder()
                .id(TRAINING_ID)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .subTrainingProgram(SubTrainingProgram.builder().name("subProgram_name").build())
                .build();
        var freePass = getFreePass();
        var freePassSuccessResult = getFreePassReadDto();

        doReturn(Optional.of(gym)).when(gymRepository).findById(GYM_ID);
        doReturn(Optional.of(training)).when(trainingRepository).findById(TRAINING_ID);
        doReturn(false).when(freePassRepository).existsFreePassByEmail(any());
        doReturn(false).when(freePassRepository).existsFreePassByPhone(any());
        doReturn(freePass).when(freePassCreateMapper).toEntity(any());
        doReturn(freePass).when(freePassRepository).saveAndFlush(freePass);
        doReturn(freePassSuccessResult).when(freePassReadDtoMapper).toDto(freePass);

        doNothing().when(emailService).sendSimpleEmail(eq(freePass.getEmail()), eq("ПРОБНОЕ ЗАНЯТИЕ"), any());

        var actualResult = freePassServiceImpl.create(getFreePassCreateDto());

        assertThat(actualResult).isNotNull();
        Assertions.assertAll(
                () -> assertThat(actualResult.getFirstname()).isEqualTo(freePassSuccessResult.getFirstname()),
                () -> assertThat(actualResult.getLastname()).isEqualTo(freePassSuccessResult.getLastname()),
                () -> assertThat(actualResult.getPhone()).isEqualTo(freePassSuccessResult.getPhone()),
                () -> assertThat(actualResult.getEmail()).isEqualTo(freePassSuccessResult.getEmail()),
                () -> assertThat(actualResult.getDate()).isEqualTo(freePassSuccessResult.getDate()),
                () -> assertThat(actualResult.getStart()).isEqualTo(freePassSuccessResult.getStart()),
                () -> assertThat(actualResult.getEnd()).isEqualTo(freePassSuccessResult.getEnd()),
                () -> assertThat(actualResult.getGymId()).isEqualTo(freePassSuccessResult.getGymId()),
                () -> assertThat(actualResult.getTrainingId()).isEqualTo(freePassSuccessResult.getTrainingId()),
                () -> assertThat(actualResult.getTrainingName()).isEqualTo(freePassSuccessResult.getTrainingName())
        );


        verify(freePassRepository).existsFreePassByEmail(any());
        verify(freePassRepository).existsFreePassByPhone(any());

        verify(freePassCreateMapper).toEntity(any());
        verify(freePassRepository).saveAndFlush(any());
        verify(freePassReadDtoMapper).toDto(any());
    }

    private FreePass getFreePass() {
        return FreePass.builder()
                .id(null)
                .firstname("freepass_firstname")
                .lastname("freepass_lastname")
                .phone("88005553535")
                .email("freepass_test@test.ru")
                .isDone(true)
                .date(date)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .trainingName("subProgram_name_monday")
                .gym(Gym.builder().id(1L).build())
                .training(Training.builder().id(1L).build())
                .build();
    }

    private FreePassReadDto getFreePassReadDto() {
        return FreePassReadDto.builder()
                .firstname("free_pass_name")
                .lastname("free_pass_firstName")
                .phone("88005553535")
                .email("freepass_test@test.ru")
                .isDone(false)
                .date(LocalDate.now())
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .gymId(1L)
                .trainingId(1L)
                .trainingName("subProgram_name_monday")
                .training(null)
                .build();
    }

    private FreePassCreateDto getFreePassCreateDto() {
        return FreePassCreateDto.builder()
                .firstName("free_pass_name")
                .lastName("free_pass_firstName")
                .phone("88005553535")
                .email("freepass_test@test.ru")
                .gymId(GYM_ID)
                .trainingId(TRAINING_ID)
                .date(LocalDate.now())
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .build();
    }

    private FreePassCreateDto getFreePassCreateDtoWithGymIdNotExist() {
        return FreePassCreateDto.builder()
                .firstName("freepass_firstname")
                .lastName("freepass_lastname")
                .phone("88005553535")
                .email("freepass_test@test.ru")
                .gymId(GYM_ID_NOT_EXIST)
                .trainingId(TRAINING_ID_NOT_EXIST)
                .date(LocalDate.now())
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .build();
    }

    private List<TrainingReadDto> getTrainingReadDtos() {
        var training_monday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(1)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name_monday")
                .programId(1L)
                .build();

        var training_tuesday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(2)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name")
                .programId(1L)
                .build();
        var training_wednesday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(3)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name")
                .programId(1L)
                .build();

        var training_thursday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(4)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name")
                .programId(1L)
                .build();

        var training_friday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(5)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name")
                .programId(1L)
                .build();

        var training_saturday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(6)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name")
                .programId(1L)
                .build();

        var training_sunday = TrainingReadDto.builder()
                .id(1L)
                .title("Training_title")
                .start(LocalTime.of(9, 0))
                .end(LocalTime.of(10, 0))
                .dayOfWeek(7)
                .date(LocalDate.now())
                .takenSeats(10)
                .totalSeats(25)
                .coachId(1L)
                .coach("coach_name")
                .gymId(1L)
                .roomId(1L)
                .room("room_name")
                .subProgramId(1L)
                .subProgram("subProgram_name")
                .programId(1L)
                .build();

        return List.of(
                training_monday,
                training_tuesday,
                training_wednesday,
                training_thursday,
                training_friday,
                training_saturday,
                training_sunday
        );
    }
}