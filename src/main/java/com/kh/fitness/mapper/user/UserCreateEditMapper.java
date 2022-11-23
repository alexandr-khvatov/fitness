package com.kh.fitness.mapper.user;

import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.entity.Role;
import com.kh.fitness.entity.User;
import com.kh.fitness.exception.UserRoleException;
import com.kh.fitness.mapper.Mapper;
import com.kh.fitness.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateDto, User> {
    private final RoleRepository roleRepository;

    @Override
    public User map(UserCreateDto f, User t) {
        copy(f, t);
        return t;
    }

    @Override
    public User map(UserCreateDto f) {
        User user = new User();
        copy(f, user);
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
        user.setPassword(o.getPassword());

        var maybeRoles = rolesResolver(o.getRoles());
        if (!maybeRoles.isEmpty()) {
            user.setRoles(maybeRoles);
        } else {
            log.error("Role set is empty or cannot be resolved, for creating user: {},list of roles id that cannot be resolved: {}", user, o.getRoles());
            throw new UserRoleException("Roles set that cannot be resolved");
        }

//        Optional.ofNullable(o.getImage())
//                .filter(not(MultipartFile::isEmpty))
//                .ifPresent(image -> user.setImage(image.getOriginalFilename()));

//        Optional.ofNullable(o.getPassword())
//                .filter(StringUtils::hasText)
//                .map(passwordEncoder::encode)
//                .ifPresent(user::setPassword);
    }

    private Set<Role> rolesResolver(Set<Long> roles) {
        if(roles.contains(1L) || roles.contains(2L)){
            roles.add(3L);
        }
        return roleRepository.findAll().stream()
                .filter(role -> roles.contains(role.getId()))
                .collect(toSet());
    }
}