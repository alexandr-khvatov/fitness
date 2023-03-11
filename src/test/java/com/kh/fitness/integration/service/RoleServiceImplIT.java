package com.kh.fitness.integration.service;

import com.kh.fitness.integration.IntegrationTestBase;
import com.kh.fitness.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.kh.fitness.model_builder.RoleTestBuilder.getRole;
import static com.kh.fitness.model_builder.RoleTestBuilder.getRoleWithId;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class RoleServiceImplIT extends IntegrationTestBase {

    private final RoleServiceImpl roleService;

    public static final Long ROLE_ID = 1L;
    public static final Long ROLE_ID_NOT_EXIST_IN_DB = -128L;

    @Test
    void findById_shouldFindRole_whenExist() {
        var actualResult = roleService.findById(ROLE_ID);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(ROLE_ID);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        var actualResult = roleService.findById(ROLE_ID_NOT_EXIST_IN_DB);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findByName_shouldFindRole_whenExist() {
        var role = getRole();

        var actualResult = roleService.findByName(role.getName());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getName()).isEqualTo(role.getName());
    }

    @RepeatedTest(3)
    void findAll_shouldFindRoles_whenExist() {
        var actualResult = roleService.findAll();

        assertThat(actualResult).hasSize(3).containsAnyOf(getRoleWithId(ROLE_ID));
    }

    @Sql(statements = {"TRUNCATE role CASCADE;"})
    @Test
    void findAll_shouldReturnEmpty_whenNotExist() {
        var actualResult = roleService.findAll();

        assertThat(actualResult).isEmpty();
    }
}