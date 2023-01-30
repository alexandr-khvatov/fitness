package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassEditTrainingDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.training.TrainingReadMapper;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.TrainingMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {GymMapperResolver.class, TrainingMapperResolver.class, TrainingReadMapper.class})
public interface FreePassReadDtoMapper {
    @Mapping(target = "start", ignore = true)
    @Mapping(target = "end", ignore = true)
    @Mapping(target = "gymId", source = "gym.id")
    @Mapping(target = "trainingId", source = "training.id")
    @Mapping(target = "training", source = "training")
    @Mapping(target = "trainingName", source = "trainingName")
    FreePassReadDto toDto(FreePass s);
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
    FreePass updateEntity(FreePassEditTrainingDto s, @MappingTarget FreePass t);
}

//@Component
//@RequiredArgsConstructor
//public class FreePassReadDtoMapper implements Mapper<FreePass, FreePassReadDto> {
//
//    private final TrainingReadMapper trainingReadMapper;
//
//    @Override
//    public FreePassReadDto map(FreePass from) {
//        return mapToDto(from);
//    }
//
//    public FreePass map(FreePassReadDto f) {
//        return mapToEntity(f);
//    }
//
//    private FreePass mapToEntity(FreePassReadDto from) {
//        var freePass = new FreePass();
//        freePass.setFirstname(from.getFirstname());
//        freePass.setLastname(from.getLastname());
//        freePass.setPhone(from.getPhone());
//        freePass.setEmail(from.getEmail());
//        return freePass;
//    }
//
//    private FreePassReadDto mapToDto(FreePass from) {
//        if (from.getTraining()==null){
//            return new FreePassReadDto(
//                    from.getId(),
//                    from.getFirstname(),
//                    from.getLastname(),
//                    from.getPhone(),
//                    from.getEmail(),
//                    from.getIsDone(),
//                    from.getDate(),
//                    from.getStartTime(),
//                    from.getEndTime(),
//                    null,
//                    null,
//                    from.getTrainingName(),
//                    null
//            );
//        }
//        return new FreePassReadDto(
//                from.getId(),
//                from.getFirstname(),
//                from.getLastname(),
//                from.getPhone(),
//                from.getEmail(),
//                from.getIsDone(),
//                from.getDate(),
//                from.getStartTime(),
//                from.getEndTime(),
//                from.getTraining().getGym().getId(),
//                from.getTraining().getId(),
//                from.getTrainingName(),
//                trainingReadMapper.map(from.getTraining())
//        );
//    }
//}
