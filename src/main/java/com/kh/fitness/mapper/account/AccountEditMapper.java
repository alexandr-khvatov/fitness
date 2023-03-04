package com.kh.fitness.mapper.account;

import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.entity.user.User;
import com.kh.fitness.mapper.util.resolvers.GymMapperResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {GymMapperResolver.class})
public interface AccountEditMapper {
    @Mapping(target = "gym", source = "gymId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User updateEntity(AccountEditDto source, @MappingTarget User target);
}