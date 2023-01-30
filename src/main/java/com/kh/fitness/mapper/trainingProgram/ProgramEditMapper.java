package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.trainingProgram.ProgramEditDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {GymMapperResolver.class})
public interface ProgramEditMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "subTrainingPrograms", ignore = true)
    @Mapping(target = "gym", source = "gymId")
    TrainingProgram toEntity(ProgramEditDto s);

    @InheritConfiguration
    TrainingProgram updateEntity(ProgramEditDto s, @MappingTarget TrainingProgram t);

}
//@Component
//@RequiredArgsConstructor
//public class ProgramEditMapper implements Mapper<ProgramEditDto, TrainingProgram> {
//    private final GymRepository gymRepository;
//
//    @Override
//    public TrainingProgram map(ProgramEditDto f) {
//        var program = new TrainingProgram();
//        copy(f, program);
//        return program;
//    }
//
//    public TrainingProgram map(ProgramEditDto f, TrainingProgram t) {
//        copy(f, t);
//        return t;
//    }
//
//    public Gym getGym(Long gymId) {
//        return Optional.ofNullable(gymId)
//                .flatMap(gymRepository::findById)
//                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
//    }
//
//    private void copy(ProgramEditDto f, TrainingProgram t) {
//        t.setName(f.getName());
//        t.setOverview(f.getOverview());
//        t.setDescription(f.getDescription());
//        t.setGym(getGym(f.getGymId()));
//    }
//}
