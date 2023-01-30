package com.kh.fitness.mapper.training;

import com.kh.fitness.dto.training.TrainingCreateDto;
import com.kh.fitness.entity.Training;
import com.kh.fitness.mapper.util.resolvers.CoachMapperResolver;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.RoomMapperResolver;
import com.kh.fitness.mapper.util.resolvers.SubTrainingProgramMapperResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.DayOfWeek;

@Mapper(uses = {
        SubTrainingProgramMapperResolver.class,
        RoomMapperResolver.class,
        CoachMapperResolver.class,
        GymMapperResolver.class
})
public interface TrainingCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "subTrainingProgram", source = "subProgramId")
    @Mapping(target = "room", source = "roomId")
    @Mapping(target = "coach", source = "coachId")
    @Mapping(target = "gym", source = "gymId")
    @Mapping(target = "dayOfWeek", expression = "java(dayOfWeek(s.getDayOfWeek()))")
    Training toEntity(TrainingCreateDto s);

    default DayOfWeek dayOfWeek(Integer day) {
        return DayOfWeek.of(day);
    }
}

/*@Component
@RequiredArgsConstructor
public class TrainingCreateMapper implements Mapper<TrainingCreateDto, Training> {
    private final ProgramRepository programRepository;
    private final GymRepository gymRepository;
    private final CoachRepository coachRepository;
    private final RoomRepository roomRepository;
    private final SubProgramRepository subProgramRepository;

    @Override
    public Training map(TrainingCreateDto f) {
        var training = new Training();
        copy(f, training);
        return training;
    }

    private void copy(TrainingCreateDto f, Training t) {
        t.setName(f.getTitle());
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
}*/
