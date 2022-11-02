package com.kh.fitness.mapper;

import com.kh.fitness.dto.FreePassRequestCreateDto;
import com.kh.fitness.entity.FreePassRequest;
import org.springframework.stereotype.Component;

@Component
public class FreePassRequestCreateDtoMapper implements Mapper<FreePassRequestCreateDto, FreePassRequest> {

    @Override
    public FreePassRequest map(FreePassRequestCreateDto from) {
        return mapToEntity(from);
    }


    public FreePassRequestCreateDto map(FreePassRequest from) {
        return mapToEntity(from);
    }

    private FreePassRequest mapToEntity(FreePassRequestCreateDto from) {
        return new FreePassRequest(
                null,
                from.getFirstname(),
                from.getLastname(),
                from.getPhone(),
                from.getEmail(),
                true);
    }

    private FreePassRequestCreateDto mapToEntity(FreePassRequest from) {
        return new FreePassRequestCreateDto(
                from.getFirstname(),
                from.getLastname(),
                from.getPhone(),
                from.getEmail());
    }
}
