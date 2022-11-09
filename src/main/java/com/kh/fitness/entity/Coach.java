package com.kh.fitness.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@EqualsAndHashCode(exclude = {"trainings"})
@ToString(exclude = {"trainings"})
@Data
@Entity
public class Coach implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String patronymic;
    private String lastname;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String specialization;
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false, fetch = LAZY)
    private Gym gym;

    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings;
}