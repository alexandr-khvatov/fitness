package com.kh.fitness.dto;

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