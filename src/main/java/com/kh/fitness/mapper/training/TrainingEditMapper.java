package com.kh.fitness.mapper.training;

import com.kh.fitness.dto.training.TrainingEditDto;
import com.kh.fitness.entity.*;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.CoachRepository;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.RoomRepository;
import com.kh.fitness.repository.SubProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TrainingEditMapper implements Mapper<TrainingEditDto, Training> {
    private final GymRepository gymRepository;
    private final CoachRepository coachRepository;
    private final RoomRepository roomRepository;
    private final SubProgramRepository subProgramRepository;

    @Override
    public Training map(TrainingEditDto f) {
        var training = new Training();
        copy(f, training);
        return training;
    }

    @Override
    public Training map(TrainingEditDto f,Training t) {
        copy(f, t);
        return t;
    }

    private void copy(TrainingEditDto f, Training t) {
        t.setName(f.getName());
        t.setStartTime(f.getStart());
        t.setEndTime(f.getEnd());
        t.setDayOfWeek(DayOfWeek.of(f.getDayOfWeek()));
        t.setDate(f.getDate());
        t.setTotalSeats(f.getTotalSeats());
        t.setTakenSeats(f.getTakenSeats());
        t.setCoach(getCoach(f.getCoachId()));
        t.setGym(getGym(f.getGymId()));
        t.setRoom(getRoom(f.getRoomId()));
        t.setSubTrainingProgram(getSubTrainingProgram(f.getSubProgramId()));
    }

    private SubTrainingProgram getSubTrainingProgram(Long subProgramId) {
        return Optional.ofNullable(subProgramId)
                .flatMap(subProgramRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity SubTrainingProgram not found with id: " + subProgramId));
    }

    public Room getRoom(Long roomId) {
        return Optional.ofNullable(roomId)
                .flatMap(roomRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Coach not found with id: " + roomId));
    }

    public Coach getCoach(Long coachId) {
        return Optional.ofNullable(coachId)
                .flatMap(coachRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Coach not found with id: " + coachId));
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
