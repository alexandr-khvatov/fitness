package com.kh.fitness.repository;

import com.kh.fitness.entity.FreePass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreePassRepository extends JpaRepository<FreePass, Long> {
  List<FreePass> findAllByGymIdOrderByIsDone(Long gymId);
  List<FreePass> findAllByTrainingId(Long trainingId);
  boolean existsUserByEmail(String email);

  boolean existsUserByPhone(String phone);
}