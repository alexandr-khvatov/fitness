package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassEditTrainingDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.util.resolvers.TrainingMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {TrainingMapperResolver.class})
public interface FreePassEditTrainingMapper {
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
    @Mapping(target = "gym", ignore = true)
    @Mapping(target = "isDone", constant = "false")
    @Mapping(target = "training", source = "trainingId")
    FreePass toEntity(FreePassEditTrainingDto s);

    @InheritConfiguration
    @Mapping(target = "isDone",ignore = true)
    FreePass updateEntity(FreePassEditTrainingDto s, @MappingTarget FreePass t);
}

//@Component
//@RequiredArgsConstructor
//public class FreePassEditTrainingMapper implements Mapper<FreePassEditTrainingDto, FreePass> {
//
//    private final TrainingRepository trainingRepository;
//    private final GymRepository gymRepository;
//    @Override
//    public FreePass map(FreePassEditTrainingDto f) {
//        return mapToEntity(f);
//    }
//
//
//    public FreePass map(FreePassEditTrainingDto from,FreePass to) {
//        var training=getTraining(from.getTrainingId());
//        to.setTraining(training);
//        to.setDate(from.getDate());
//        to.setStartTime(training.getStartTime());
//        to.setEndTime(training.getEndTime());
//        to.setTrainingName(training.getSubTrainingProgram().getName());
//        return to;
//    }
//
//    private FreePass mapToEntity(FreePassEditTrainingDto from) {
//        var freePass = new FreePass();
////        freePass.setFirstname(from.getFirstName());
////        freePass.setLastname(from.getLastName());
////        freePass.setPhone(from.getPhone());
////        freePass.setEmail(from.getEmail());
////        freePass.setIsDone(false);
////        freePass.setDate(from.getDate());
////        freePass.setStartTime(from.getStart());
////        freePass.setEndTime(from.getEnd());
////        freePass.setGym(getGym(from.getGymId()));
////        freePass.setTraining(getTraining(from.getTrainingId()));
//        return freePass;
//    }
//
////    private FreePassCreateDto mapToDto(FreePass from) {
////        return new FreePassCreateDto(
////                from.getFirstname(),
////                from.getLastname(),
////                from.getPhone(),
////                from.getEmail(),
////                from.getGym().getId()
////        );
////    }
//
//    public Training getTraining(Long trainingId) {
//        return Optional.ofNullable(trainingId)
//                .flatMap(trainingRepository::findById)
//                .orElseThrow(() -> new EntityNotFoundException("Entity Training not found with id: " + trainingId));
//    }
//    public Gym getGym(Long gymId) {
//        return Optional.ofNullable(gymId)
//                .flatMap(gymRepository::findById)
//                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
//    }
//}
