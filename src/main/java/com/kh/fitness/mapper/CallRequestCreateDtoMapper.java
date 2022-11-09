package com.kh.fitness.mapper;

import com.kh.fitness.dto.CallRequestCreateDto;
import com.kh.fitness.entity.CallRequest;
import org.springframework.stereotype.Component;

@Component
public class CallRequestCreateDtoMapper implements Mapper<CallRequestCreateDto, CallRequest> {

    @Override
    public CallRequest map(CallRequestCreateDto f) {
        return mapToEntity(f);
    }

    private CallRequest mapToEntity(CallRequestCreateDto f) {
        return new CallRequest(
                null,
                f.getPhone(),
                f.getFirstname(),
                f.getLastname(),
                true);
    }
}
