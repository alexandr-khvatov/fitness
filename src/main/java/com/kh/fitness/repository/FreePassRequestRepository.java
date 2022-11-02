package com.kh.fitness.repository;

import com.kh.fitness.entity.FreePassRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreePassRequestRepository extends JpaRepository<FreePassRequest, Long> {
}