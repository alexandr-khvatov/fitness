package com.kh.fitness.repository;

import com.kh.fitness.entity.CallRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRequestRepository extends JpaRepository<CallRequest, Long> {
}