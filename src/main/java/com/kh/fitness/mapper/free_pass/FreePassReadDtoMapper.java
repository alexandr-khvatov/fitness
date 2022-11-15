package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class FreePassReadDtoMapper implements Mapper<FreePass, FreePassReadDto> {

    @Override
    public FreePassReadDto map(FreePass from) {
        return mapToDto(from);
    }

    public FreePass map(FreePassReadDto f) {
        return mapToEntity(f);
    }

    private FreePass mapToEntity(FreePassReadDto from) {
        var freePass = new FreePass();
        freePass.setFirstname(from.getFirstname());
        freePass.setLastname(from.getLastname());
        freePass.setPhone(from.getPhone());
        freePass.setEmail(from.getEmail());
        return freePass;
    }

    private FreePassReadDto mapToDto(FreePass from) {
        return new FreePassReadDto(
                from.getId(),
                from.getFirstname(),
                from.getLastname(),
                from.getPhone(),
                from.getEmail(),
                from.getIsDone()
        );
    }
}
