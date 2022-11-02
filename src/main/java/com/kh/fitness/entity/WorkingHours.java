package com.kh.fitness.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public
class WorkingHours {
    private String workingHoursOnWeekdays;
    private String workingHoursOnWeekends;
}
