package com.kh.fitness.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TrainingProgram implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String overview;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
}