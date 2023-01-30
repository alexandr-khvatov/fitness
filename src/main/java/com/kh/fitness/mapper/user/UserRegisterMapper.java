package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserRegisterMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "image",ignore = true)
    @Mapping(target = "roles",ignore = true)
    @Mapping(target = "authorities",ignore = true)
    User toEntity(UserRegisterDto s);
}
/*
@Component
@RequiredArgsConstructor
public class UserRegisterMapper implements Mapper<UserRegisterDto, User> {
    @Override
    public User map(UserRegisterDto f) {
        User user = new User();
        copy(f, user);
        return user;
    }

    private void copy(UserRegisterDto o, User user) {
        user.setEmail(o.getEmail());
        user.setPhone(o.getPhone());
        user.setFirstname(o.getFirstname());
        user.setPatronymic(o.getPatronymic());
        user.setLastname(o.getLastname());
        user.setBirthDate(o.getBirthDate());
        user.setPassword(o.getPassword());
    }
}*/
