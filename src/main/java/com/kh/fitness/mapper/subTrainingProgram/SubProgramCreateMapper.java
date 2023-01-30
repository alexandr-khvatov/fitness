package com.kh.fitness.mapper.subTrainingProgram;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.subTrainingProgram.SubProgramCreateDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.mapper.util.resolvers.TrainingProgramMapperResolver;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(uses = {TrainingProgramMapperResolver.class})
public abstract class SubProgramCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "trainingProgram", source = "programId")
    public abstract SubTrainingProgram toEntity(SubProgramCreateDto s);

    @AfterMapping
    protected void setImageName(CoachCreateDto source, @MappingTarget SubTrainingProgram subTrainingProgram) {
        Optional.ofNullable(source.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> subTrainingProgram.setImage(image.getOriginalFilename()));
    }
}
//@Component
//@RequiredArgsConstructor
//public class SubProgramCreateMapper implements Mapper<SubProgramCreateDto, SubTrainingProgram> {
//    private final ProgramRepository programRepository;
//
//    @Override
//    public SubTrainingProgram map(SubProgramCreateDto f) {
//        var program = new SubTrainingProgram();
//        copy(f, program);
//        return program;
//    }
//
//    private void copy(SubProgramCreateDto f, SubTrainingProgram t) {
//        t.setName(f.getName());
//        t.setOverview(f.getOverview());
//        t.setDescription(f.getDescription());
//        t.setTrainingProgram(getTrainingProgram(f.getProgramId()));
//        Optional.ofNullable(f.getImage())
//                .filter(not(MultipartFile::isEmpty))
//                .ifPresent(image -> t.setImage(image.getOriginalFilename()));
//    }
//
//    private TrainingProgram getTrainingProgram(Long programId) {
//        return Optional.ofNullable(programId)
//                .flatMap(programRepository::findById)
//                .orElseThrow(() -> new EntityNotFoundException("Entity TrainingProgram not found with id: " + programId));
//    }
//}
