package com.kh.fitness.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.time.LocalTime;

import static javax.persistence.EnumType.ORDINAL;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Embeddable
public class GymOpeningHourInfo {

    @Enumerated(value = ORDINAL)
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isOpen;
}
