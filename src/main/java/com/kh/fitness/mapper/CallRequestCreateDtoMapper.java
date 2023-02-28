package com.kh.fitness.mapper;

import com.kh.fitness.dto.call_request.CallRequestCreateDto;
import com.kh.fitness.entity.CallRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Mapper
@Primary
public interface CallRequestCreateDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    CallRequest toEntity(CallRequestCreateDto source);
}