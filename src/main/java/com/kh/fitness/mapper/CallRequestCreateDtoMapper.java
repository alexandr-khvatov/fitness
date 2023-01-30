package com.kh.fitness.mapper;

import com.kh.fitness.dto.CallRequestCreateDto;
import com.kh.fitness.entity.CallRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CallRequestCreateDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    CallRequest toEntity(CallRequestCreateDto source);
}
//@Component
//public class CallRequestCreateDtoMapper implements Mapper<CallRequestCreateDto, CallRequest> {
//
//    @Override
//    public CallRequest map(CallRequestCreateDto f) {
//        return mapToEntity(f);
//    }
//
//    private CallRequest mapToEntity(CallRequestCreateDto f) {
//        return new CallRequest(
//                null,
//                f.getPhone(),
//                f.getFirstname(),
//                f.getLastname(),
//                true);
//    }
//}
