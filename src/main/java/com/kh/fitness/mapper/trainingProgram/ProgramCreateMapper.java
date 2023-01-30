package com.kh.fitness.mapper.trainingProgram;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.trainingProgram.ProgramCreateDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import com.kh.fitness.mapper.util.resolvers.TrainingProgramMapperResolver;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(uses = {GymMapperResolver.class, TrainingProgramMapperResolver.class})
public abstract class ProgramCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "subTrainingPrograms", ignore = true)
    @Mapping(target = "gym", source = "gymId")
    public abstract TrainingProgram toEntity(ProgramCreateDto f);

    @AfterMapping
    protected void setImageName(ProgramCreateDto source, @MappingTarget TrainingProgram trainingProgram) {
        Optional.ofNullable(source.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> trainingProgram.setImage(image.getOriginalFilename()));
    }
}
/*@Component
@RequiredArgsConstructor
public class ProgramCreateMapper implements Mapper<ProgramCreateDto, TrainingProgram> {
    private final GymRepository gymRepository;

    @Override
    public TrainingProgram map(ProgramCreateDto f) {
        var program = new TrainingProgram();
        program.setName(f.getName());
        program.setOverview(f.getOverview());
        program.setDescription(f.getDescription());
        program.setGym(getGym(f.getGymId()));

        Optional.ofNullable(f.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> program.setImage(image.getOriginalFilename()));

        return program;
    }

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}*/
