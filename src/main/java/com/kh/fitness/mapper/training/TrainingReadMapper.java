package com.kh.fitness.mapper.training;

import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.entity.Training;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.mapper.coach.CoachReadDtoMapper;
import com.kh.fitness.mapper.room.RoomReadMapper;
import com.kh.fitness.mapper.subTrainingProgram.SubProgramReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingReadMapper implements Mapper<Training, TrainingReadDto> {
    private final CoachReadDtoMapper coachReadDtoMapper;
    private final SubProgramReadMapper subProgramReadMapper;
    private final RoomReadMapper roomReadMapper;

    @Override
    public TrainingReadDto map(Training f) {
        var coach = coachReadDtoMapper.map(f.getCoach());
        var room = roomReadMapper.map(f.getRoom());
        var subProgram = subProgramReadMapper.map(f.getSubTrainingProgram());
        return TrainingReadDto.builder()
                .id(f.getId())
                .title(f.getName())
                .start(f.getStartTime())
                .end(f.getEndTime())
                .date(f.getDate())
                .dayOfWeek(f.getDayOfWeek().getValue())
                .totalSeats(f.getTotalSeats())
                .takenSeats(f.getTakenSeats())
                .coachId(coach.getId())
                .coach(coach.getFirstname())
                .gymId(f.getGym().getId())
                .roomId(room.getId())
                .room(room.getName())
                .subProgramId(subProgram.getId())
                .subProgram(subProgram.getName())
                .programId(subProgram.getProgramId())
                .build();
    }
}