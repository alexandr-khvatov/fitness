package com.kh.fitness.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.*;

@ToString(exclude = {"coaches","trainingPrograms","subscriptions"})
@EqualsAndHashCode(of = "name")
@Entity
@Data
public class Gym implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private GymContacts gymContacts;
    private WorkingHours workingHours;
    private GymSocialMedia gymSocialMedia;

    @OneToMany(mappedBy = "gym", cascade = ALL)
    private Set<Coach> coaches = new HashSet<>();

    @OneToMany(mappedBy = "gym", cascade = ALL)
    private List<TrainingProgram> trainingPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = ALL)
    private List<Subscription> subscriptions = new ArrayList<>();
}