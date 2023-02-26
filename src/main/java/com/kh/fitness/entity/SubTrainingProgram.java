package com.kh.fitness.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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