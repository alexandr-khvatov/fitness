package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassEditTrainingDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.Training;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import com.kh.fitness.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FreePassEditTrainingMapper implements Mapper<FreePassEditTrainingDto, FreePass> {

    private final TrainingRepository trainingRepository;
    private final GymRepository gymRepository;
    @Override
    public FreePass map(FreePassEditTrainingDto f) {
        return mapToEntity(f);
    }


    public FreePass map(FreePassEditTrainingDto from,FreePass to) {
        var training=getTraining(from.getTrainingId());
        to.setTraining(training);
        to.setDate(from.getDate());
        to.setStartTime(training.getStartTime());
        to.setEndTime(training.getEndTime());
        to.setTrainingName(training.getSubTrainingProgram().getName());
        return to;
    }

    private FreePass mapToEntity(FreePassEditTrainingDto from) {
        var freePass = new FreePass();
//        freePass.setFirstname(from.getFirstName());
//        freePass.setLastname(from.getLastName());
//        freePass.setPhone(from.getPhone());
//        freePass.setEmail(from.getEmail());
//        freePass.setIsDone(false);
//        freePass.setDate(from.getDate());
//        freePass.setStartTime(from.getStart());
//        freePass.setEndTime(from.getEnd());
//        freePass.setGym(getGym(from.getGymId()));
//        freePass.setTraining(getTraining(from.getTrainingId()));
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
}
