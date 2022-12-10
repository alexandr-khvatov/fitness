package com.kh.fitness.dto.room;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateDto {
    private String name;
    private Long gymId;
}
