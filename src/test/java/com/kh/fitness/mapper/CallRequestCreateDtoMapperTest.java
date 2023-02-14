package com.kh.fitness.mapper;

import com.kh.fitness.dto.callRequest.CallRequestCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CallRequestCreateDtoMapperImpl.class })
class CallRequestCreateDtoMapperTest {
    @Autowired
    CallRequestCreateDtoMapper mapper;

    @Test
    void toEntity() {
        var dto = new CallRequestCreateDto("FirstName", "LastName", "+78005553535");
        var callRequest = mapper.toEntity(dto);

        assertEquals(dto.getFirstname(), callRequest.getFirstname());
        assertEquals(dto.getLastname(), callRequest.getLastname());
        assertEquals(dto.getPhone(), callRequest.getPhone());
    }
}