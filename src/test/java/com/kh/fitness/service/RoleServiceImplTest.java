package com.kh.fitness.service;

import com.kh.fitness.entity.user.Role;
import com.kh.fitness.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void findById_shouldFindRole_whenExist() {
        var roleId = 1L;
        var role = Role.builder()
                .id(roleId)
                .name("ADMIN")
                .build();

        doReturn(Optional.of(role)).when(roleRepository).findById(roleId);
        var actual = roleService.findById(roleId);

        assertThat(actual).isNotNull().isEqualTo(Optional.of(role));
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        doReturn(Optional.empty()).when(roleRepository).findById(anyLong());
        var actual = roleService.findById(anyLong());

        assertThat(actual).isEmpty();
    }
}