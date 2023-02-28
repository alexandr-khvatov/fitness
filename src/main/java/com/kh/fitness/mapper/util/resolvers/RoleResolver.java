package com.kh.fitness.mapper.util.resolvers;

import com.kh.fitness.entity.user.Role;
import com.kh.fitness.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleResolver {
    public final RoleRepository roleRepository;

    public Set<Role> resolve(Set<Long> maybeRoles) {
        var roles = roleRepository.findAll().stream().collect(toMap(Role::getId, role -> role));
        if (maybeRoles.size() > roles.size()) {
            log.warn("More roles passed ({}) than available ({})", maybeRoles.size(), roles.size());
            throw new IllegalArgumentException("More roles passed (" + maybeRoles.size() + ") than available (" + roles.size() + ")");
        }

        var resolvedRoles = new HashSet<Role>();
        for (Long id : maybeRoles)
            if (roles.containsKey(id)) {
                resolvedRoles.add(roles.get(id));
            } else {
                log.warn("Role with id {} not found", id);
                throw new NoSuchElementException("Role with id" + id + " not found");
            }


        if (!resolvedRoles.isEmpty()) {
            return resolvedRoles;
        } else {
            log.warn("Role set is empty or cannot be resolved,list of roles id that cannot be resolved: {}", resolvedRoles);
            throw new NoSuchElementException("Roles set that cannot be resolved " + resolvedRoles);
        }
    }
}