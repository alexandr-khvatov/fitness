package com.kh.fitness.entity;

import com.kh.fitness.entity.gym.Gym;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@ToString(exclude = {"trainings"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings = new HashSet<>();
}