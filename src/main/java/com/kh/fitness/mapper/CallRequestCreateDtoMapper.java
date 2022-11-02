package com.kh.fitness.mapper;

import com.kh.fitness.dto.CallRequestCreateDto;
import com.kh.fitness.entity.CallRequest;
import org.springframework.stereotype.Component;

@Component
public class CallRequestCreateDtoMapper implements Mapper<CallRequestCreateDto, CallRequest> {

    @Override
    public CallRequest map(CallRequestCreateDto from) {
        return mapToEntity(from);
    }

    private CallRequest mapToEntity(CallRequestCreateDto from) {
        return new CallRequest(
                null,
                from.getPhone(),
                from.getFirstname(),
                from.getLastname(),
                true);
    }
}
