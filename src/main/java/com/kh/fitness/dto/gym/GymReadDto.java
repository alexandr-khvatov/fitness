package com.kh.fitness.dto.gym;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymReadDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String vkLink;
    private String tgLink;
    private String instLink;
    private String workingHoursOnWeekdays;
    private String workingHoursOnWeekends;
}