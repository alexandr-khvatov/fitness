package com.kh.fitness.entity;

import com.kh.fitness.entity.gym.Gym;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private Integer takenSeats;
    private Integer totalSeats;

    @ManyToOne(optional = false)
    private Coach coach;

    @ManyToOne(fetch = LAZY)
    private Gym gym;

    @ManyToOne(fetch = LAZY)
    private Room room;

    @ManyToOne(fetch = LAZY)
    private SubTrainingProgram subTrainingProgram;
}