package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserCreatedDtoMapper {

    UserCreatedDto toDto(User s);
}
/*
@Component
public class UserCreatedDtoMapper implements Mapper<User, UserCreatedDto> {

    @Override
    public UserCreatedDto map(User f) {
        return new UserCreatedDto(
                f.getId(),
                f.getFirstname(),
                f.getPatronymic(),
                f.getLastname(),
                f.getEmail(),
                f.getPhone(),
                f.getBirthDate(),
                f.getRoles());
    }
}
*/
