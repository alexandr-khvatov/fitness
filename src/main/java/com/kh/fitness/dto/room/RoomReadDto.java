package com.kh.fitness.dto.room;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomReadDto {
    private Long id;
    private String name;
}
