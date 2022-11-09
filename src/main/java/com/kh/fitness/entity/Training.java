package com.kh.fitness.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Data
@Entity
public class Training implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(value = ORDINAL)
    private DayOfWeek dayOfWeek;

    private LocalDate date;

    @ManyToOne(optional = false)
    private Coach coach;

    @ManyToOne(fetch = LAZY)
    private Gym gym;
}