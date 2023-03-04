package com.kh.fitness.dto.role;

import com.kh.fitness.entity.user.Roles;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleReadDto {
    private Long id;
    private Roles name;
}