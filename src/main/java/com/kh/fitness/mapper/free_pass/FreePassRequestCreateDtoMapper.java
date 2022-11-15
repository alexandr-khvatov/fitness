package com.kh.fitness.mapper.free_pass;

import com.kh.fitness.dto.free_pass.FreePassRequestCreateDto;
import com.kh.fitness.entity.FreePass;
import com.kh.fitness.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class FreePassRequestCreateDtoMapper implements Mapper<FreePassRequestCreateDto, FreePass> {

    @Override
    public FreePass map(FreePassRequestCreateDto f) {
        return mapToEntity(f);
    }


    public FreePassRequestCreateDto map(FreePass from) {
        return mapToEntity(from);
    }

    private FreePass mapToEntity(FreePassRequestCreateDto from) {
        var freePass = new FreePass();
        freePass.setFirstname(from.getFirstname());
        freePass.setLastname(from.getLastname());
        freePass.setPhone(from.getPhone());
        freePass.setEmail(from.getEmail());
        return freePass;
    }

    private FreePassRequestCreateDto mapToEntity(FreePass from) {
        return new FreePassRequestCreateDto(
                from.getFirstname(),
                from.getLastname(),
                from.getPhone(),
                from.getEmail());
    }
}
