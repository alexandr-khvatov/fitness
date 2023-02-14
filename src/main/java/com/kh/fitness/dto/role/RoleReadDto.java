package com.kh.fitness.dto.role;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleReadDto {
    private Long id;
    private String name;
}