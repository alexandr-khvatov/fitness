package com.kh.fitness.mapper.trainingProgram;


import com.kh.fitness.dto.trainingProgram.ProgramReadDto;
import com.kh.fitness.dto.trainingProgram.ProgramReadWithSubProgramsDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.mapper.subTrainingProgram.SubProgramReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {SubProgramReadMapper.class})
public interface ProgramReadMapper {
    @Mapping(target = "image", expression = "java(imageUrl(s))")
    ProgramReadDto toDto(TrainingProgram s);

    @Mapping(target = "image", expression = "java(imageUrl(s))")
    @Mapping(target = "subTrainings", source = "subTrainingPrograms")
    ProgramReadWithSubProgramsDto toDtoWithSubPrograms(TrainingProgram s);

    default String imageUrl(TrainingProgram s) {
        return s.getImage() != null ? "http://localhost:8080/api/v1/sub-programs/" + s.getId() + "/avatar" : null;
    }
}
/*@Component
@RequiredArgsConstructor
public class ProgramReadMapper implements Mapper<TrainingProgram, ProgramReadDto> {

    public ProgramReadDto map(TrainingProgram f) {
        return ProgramReadDto.builder()
                .id(f.getId())
                .name(f.getName())
                .overview(f.getOverview())
                .image(f.getImage() != null ? "http://localhost:8080/api/v1/programs/" + f.getId() + "/avatar" : null)
                .description(f.getDescription())
                .build();
    }
}*/
