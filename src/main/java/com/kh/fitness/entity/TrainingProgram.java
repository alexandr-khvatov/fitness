package com.kh.fitness.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@ToString(exclude = {"subTrainingPrograms"})
@EqualsAndHashCode(exclude = {"subTrainingPrograms"})
@Data
@Entity
public class TrainingProgram implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String overview;
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "trainingProgram",cascade = ALL)
    private Set<SubTrainingProgram> subTrainingPrograms;

    @ManyToOne(optional = false, fetch = LAZY)
    private Gym gym;
}