package com.kh.fitness.dto.gym;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymOpeningHourInfoDto {
    private Integer dayOfWeek;
    @NotEmpty
    @NotNull
    private LocalTime startTime;
    @NotEmpty
    @NotNull
    private LocalTime endTime;
    private Boolean isOpen;
}
