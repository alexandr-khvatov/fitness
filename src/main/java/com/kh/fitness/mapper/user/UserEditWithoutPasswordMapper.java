package com.kh.fitness.mapper.user;


import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.entity.Role;
import com.kh.fitness.entity.User;
import com.kh.fitness.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Mapper
public abstract class UserEditWithoutPasswordMapper {
    @Autowired
    private RoleRepository roleRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", expression = "java(rolesResolver(s.getRoles()))")
    public abstract User toEntity(UserCreateDto s);

    @InheritConfiguration
    public abstract User updateEntity(UserCreateDto s, @MappingTarget User t);

    protected Set<Role> rolesResolver(Set<Long> roles) {
        var maybeRoles = roleRepository.findAll().stream()
                .filter(role -> roles.contains(role.getId()))
                .collect(toSet());
        if (!maybeRoles.isEmpty()) {
            return maybeRoles;
        } else {
            log.error("Role set is empty or cannot be resolved,list of roles id that cannot be resolved: {}", roles);
            throw new IllegalStateException("Roles set that cannot be resolved");
        }
    }
}
/*
@Slf4j
@Component
@RequiredArgsConstructor
public class UserEditWithoutPasswordMapper implements Mapper<UserCreateDto, User> {
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

        var maybeRoles = rolesResolver(o.getRoles());
        if (!(maybeRoles.isEmpty() || maybeRoles.size() != o.getRoles().size())) {
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
        return roleRepository.findAll().stream()
                .filter(role -> roles.contains(role.getId()))
                .collect(toSet());
    }
}*/
