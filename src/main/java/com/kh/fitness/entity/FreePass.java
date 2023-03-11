package com.kh.fitness.entity;

import com.kh.fitness.entity.gym.Gym;
import com.kh.fitness.entity.util.auditing.AuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FreePass extends AuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    private String lastname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean isDone;

    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    private String trainingName;

    @ManyToOne(optional = false, fetch = LAZY)
    private Gym gym;

    @ManyToOne(fetch = LAZY)
    private Training training;
}