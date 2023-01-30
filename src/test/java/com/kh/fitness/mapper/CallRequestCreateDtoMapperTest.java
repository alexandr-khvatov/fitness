package com.kh.fitness.mapper;

import com.kh.fitness.dto.CallRequestCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallRequestCreateDtoMapperTest {
    @Autowired
    CallRequestCreateDtoMapper callRequestCreateDtoMapper;

    @Test
    void toEntity() {
        var dto = new CallRequestCreateDto("FirstName", "LastName", "+78005553535");
        var callRequest = callRequestCreateDtoMapper.toEntity(dto);

        assertEquals(dto.getFirstname(), callRequest.getFirstname());
        assertEquals(dto.getLastname(), callRequest.getLastname());
        assertEquals(dto.getPhone(), callRequest.getPhone());
    }
}