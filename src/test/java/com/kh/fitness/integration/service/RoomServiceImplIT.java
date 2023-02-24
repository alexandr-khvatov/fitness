package com.kh.fitness.integration.service;

import com.kh.fitness.entity.Role;
import com.kh.fitness.integration.IntegrationTestBase;
import com.kh.fitness.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class RoomServiceImplIT extends IntegrationTestBase {

    private final RoleServiceImpl roleService;

    public static final Long ROLE_ID = 1L;
    public static final Long ROLE_ID_NOT_EXIST_IN_DB = 11_111L;

    @Test
    void findById_shouldFindRole_whenExist() {
        var role = getExistRole();

        var actualResult = roleService.findById(ROLE_ID);

        assertThat(actualResult)
                .isPresent()
                .contains(role);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        var actualResult = roleService.findById(ROLE_ID_NOT_EXIST_IN_DB);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findByName_shouldFindRole_whenExist() {
        var role = getExistRole();

        var actualResult = roleService.findByName(role.getName());

        assertThat(actualResult)
                .isPresent()
                .contains(role);
    }

    @Test
    void findByName_shouldReturnEmptyOptional_whenMissing() {
        var role = getExistRole();
        var notExistName = role.getName() + UUID.randomUUID();

        var actualResult = roleService.findByName(notExistName);

        assertThat(actualResult).isEmpty();
    }

    private static Role getExistRole() {
        return Role.builder()
                .id(ROLE_ID)
                .name("ADMIN")
                .build();
    }
}