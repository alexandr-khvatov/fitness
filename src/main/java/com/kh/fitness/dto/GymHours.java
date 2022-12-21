package com.kh.fitness.dto;

import com.kh.fitness.dto.gym.GymOpeningHourInfoDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymHours {
    @NotEmpty
    @NotNull
    List<GymOpeningHourInfoDto> openingHours;
}
