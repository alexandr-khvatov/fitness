package com.kh.fitness.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@ToString(exclude = {"coaches", "trainingPrograms", "subscriptions"})
@EqualsAndHashCode(of = "name")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gym implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private GymContacts gymContacts;
    private GymSocialMedia gymSocialMedia;

    private LocalTime minStartTime;
    private LocalTime maxEndTime;

    @ElementCollection
    @CollectionTable(name = "gym_opening_hour_info")
    private List<GymOpeningHourInfo> openingHours = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = ALL)
    private Set<Coach> coaches = new HashSet<>();

    @OneToMany(mappedBy = "gym", cascade = ALL)
    private List<TrainingProgram> trainingPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = ALL)
    private List<Subscription> subscriptions = new ArrayList<>();
}