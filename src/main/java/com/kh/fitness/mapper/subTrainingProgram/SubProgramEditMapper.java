package com.kh.fitness.mapper.subTrainingProgram;

import com.kh.fitness.dto.subTrainingProgram.SubProgramEditDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.mapper.util.resolvers.TrainingProgramMapperResolver;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {TrainingProgramMapperResolver.class})
public interface SubProgramEditMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "trainingProgram", source = "programId")
    SubTrainingProgram toEntity(SubProgramEditDto s);

    @InheritConfiguration
    SubTrainingProgram updateEntity(SubProgramEditDto s, @MappingTarget SubTrainingProgram t);

}
//@Component
//@RequiredArgsConstructor
//public class SubProgramEditMapper implements Mapper<SubProgramEditDto, SubTrainingProgram> {
//    private final ProgramRepository programRepository;
//
//    @Override
//    public SubTrainingProgram map(SubProgramEditDto f) {
//        var program = new SubTrainingProgram();
//        copy(f, program);
//        return program;
//    }
//
//    public SubTrainingProgram map(SubProgramEditDto f, SubTrainingProgram t) {
//        copy(f, t);
//        return t;
//    }
//
//    private void copy(SubProgramEditDto f, SubTrainingProgram t) {
//        t.setName(f.getName());
//        t.setOverview(f.getOverview());
//        t.setDescription(f.getDescription());
//        t.setTrainingProgram(getTrainingProgram(f.getProgramId()));
//    }
//
//    private TrainingProgram getTrainingProgram(Long programId) {
//        return Optional.ofNullable(programId)
//                .flatMap(programRepository::findById)
//                .orElseThrow(() -> new EntityNotFoundException("Entity TrainingProgram not found with id: " + programId));
//    }
//}
