package com.kh.fitness.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Subscription implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private Period period;

    @ManyToOne(fetch = LAZY)
    private Gym gym;
}

enum Period {
    ONE_MONTH,
    THREE_MONTH,
    SIX_MONTH,
    YEAR,
}