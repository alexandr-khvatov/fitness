package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FreePassCreateMapper implements Mapper<FreePassCreateDto, FreePass> {

    private final GymRepository gymRepository;
    @Override
    public FreePass map(FreePassCreateDto f) {
        return mapToDto(f);
    }


//    public FreePassCreateDto map(FreePass from) {
//        return mapToDto(from);
//    }

    private FreePass mapToDto(FreePassCreateDto from) {
        var freePass = new FreePass();
        freePass.setFirstname(from.getFirstName());
        freePass.setLastname(from.getLastName());
        freePass.setPhone(from.getPhone());
        freePass.setEmail(from.getEmail());
        freePass.setIsDone(false);
        freePass.setDate(from.getDate());
        freePass.setStartTime(from.getStart());
        freePass.setEndTime(from.getEnd());
        freePass.setGym(getGym(from.getGymId()));
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

    public Gym getGym(Long gymId) {
        return Optional.ofNullable(gymId)
                .flatMap(gymRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("Entity Gym not found with id: " + gymId));
    }
}
