package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.TrainingMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {GymMapperResolver.class, TrainingMapperResolver.class})
public interface FreePassCreateMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstname", ignore = true)
    @Mapping(target = "lastname", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "trainingName", ignore = true)
    @Mapping(target = "isDone", constant = "false")
    @Mapping(target = "gym", source = "gymId")
    @Mapping(target = "training", source = "trainingId")
    FreePass toEntity(FreePassCreateDto f);

    @InheritConfiguration
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "trainingId", source = "training.id")
    @Mapping(target = "start", source = "startTime")
    @Mapping(target = "end", source = "endTime")
    FreePassCreateDto toDto(FreePass from);
}

/*@Component
@RequiredArgsConstructor
public class FreePassCreateMapper implements Mapper<FreePassCreateDto, FreePass> {

    private final TrainingRepository trainingRepository;
    private final GymRepository gymRepository;
    @Override
    public FreePass map(FreePassCreateDto f) {
        return mapToEntity(f);
    }


//    public FreePassCreateDto map(FreePass from) {
//        return mapToDto(from);
//    }

    private FreePass mapToEntity(FreePassCreateDto from) {
        var freePass = new FreePass();
        var training=getTraining(from.getTrainingId());
        freePass.setFirstname(from.getFirstName());
        freePass.setLastname(from.getLastName());
        freePass.setPhone(from.getPhone());
        freePass.setEmail(from.getEmail());
        freePass.setIsDone(false);
        freePass.setDate(from.getDate());
        freePass.setStartTime(from.getStart());
        freePass.setEndTime(from.getEnd());
        freePass.setGym(getGym(from.getGymId()));
        freePass.setTraining(training);
        freePass.setTrainingName(training.getSubTrainingProgram().getName());
        return freePass;
    }

//    private FreePassCreateDto mapToDto(FreePass from) {
//        return new FreePassCreateDto(
//                from.getFirstname(),
//                from.getLastname(),
//                from.getPhone(),
//                from.getEmail(),
//                from.getGym().getId()
//        );
//    }

    public Training getTraining(Long trainingId) {
        return Optional.ofNullable(trainingId)
                .flatMap(trainingRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Training not found with id: " + trainingId));
    }
    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}*/
