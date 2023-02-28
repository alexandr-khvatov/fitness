package com.kh.fitness.model_builder;

import com.kh.fitness.entity.user.Role;
import com.kh.fitness.entity.user.Roles;

import java.util.Set;

public class RoleTestBuilder {
    private static final Long id = 1L;
    private static final String name = Roles.ADMIN.name();

    public static Role getRole() {
        return Role.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Role getRoleWithId(Long id) {
        return Role.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Set<Role> getRoles() {
        return Set.of(
                getRole(),
                Role.builder()
                        .id(2L)
                        .name(Roles.MANAGER.name())
                        .build(),
                Role.builder()
                        .id(3L)
                        .name(Roles.CUSTOMER.name())
                        .build());
    }
}
