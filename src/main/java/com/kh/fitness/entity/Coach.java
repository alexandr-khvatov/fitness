package com.kh.fitness.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    @OneToMany
    private List<Training> trainings;
}