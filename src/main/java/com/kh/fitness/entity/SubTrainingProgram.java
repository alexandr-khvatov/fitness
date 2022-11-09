package com.kh.fitness.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class SubTrainingProgram implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String overview;
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false,fetch= LAZY)
    private TrainingProgram trainingProgram;
}