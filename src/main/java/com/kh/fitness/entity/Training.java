package com.kh.fitness.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.*;

@Data
@Entity
public class Training implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(value = EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;
}