package com.kh.fitness.entity;

public interface BaseEntity<ID> {
    ID getId();

    void setId(ID id);
}