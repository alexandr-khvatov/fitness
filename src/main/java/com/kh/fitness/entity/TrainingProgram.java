package com.kh.fitness.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@ToString(exclude = {"subTrainingPrograms"})
@EqualsAndHashCode(exclude = {"subTrainingPrograms"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "trainingProgram", cascade = ALL)
    private Set<SubTrainingProgram> subTrainingPrograms = new HashSet<>();

    @ManyToOne(optional = false, fetch = LAZY)
    private Gym gym;
}