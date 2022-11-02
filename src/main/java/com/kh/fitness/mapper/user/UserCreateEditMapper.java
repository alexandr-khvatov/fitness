package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.entity.Role;
import com.kh.fitness.entity.User;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateDto, User> {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User map(UserCreateDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateDto o, User user) {
        Optional.ofNullable(o.getRoles()).orElseThrow();

        user.setEmail(o.getEmail());
        user.setPhone(o.getPhone());
        user.setFirstname(o.getFirstname());
        user.setPatronymic(o.getPatronymic());
        user.setLastname(o.getLastname());
        user.setBirthDate(o.getBirthDate());
        user.setRoles(rolesResolver(o.getRoles()));

//        Optional.ofNullable(o.getImage())
//                .filter(not(MultipartFile::isEmpty))
//                .ifPresent(image -> user.setImage(image.getOriginalFilename()));

        Optional.ofNullable(o.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }

    private Set<Role> rolesResolver(Set<Long> roles) {
        return roleRepository.findAll().stream()
                .filter(role -> roles.contains(role.getId()))
                .collect(toSet());
    }
}