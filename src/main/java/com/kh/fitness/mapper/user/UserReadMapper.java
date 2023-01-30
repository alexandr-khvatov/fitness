package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.entity.Role;
import com.kh.fitness.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper
public interface UserReadMapper {
    @Mapping(target = "roles", expression = "java(roles(s.getRoles()))")
    UserReadDto toDto(User s);

    default Set<Long> roles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(toSet());
    }
}
/*@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User f) {
        return new UserReadDto(f.getId(),
                f.getFirstname(),
                f.getPatronymic(),
                f.getLastname(),
                f.getEmail(),
                f.getPhone(),
                f.getBirthDate(),
                f.getRoles().stream()
                        .map(Role::getId)
                        .collect(toSet()));
    }
}*/
