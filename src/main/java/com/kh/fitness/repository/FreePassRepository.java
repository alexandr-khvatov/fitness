package com.kh.fitness.repository;

import com.kh.fitness.entity.FreePass;
import com.kh.fitness.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FreePassRepository extends JpaRepository<FreePass, Long> {
  public  List<FreePass> findAllByGymIdOrderByIsDone(Long gymId);
  boolean existsUserByEmail(String email);

  boolean existsUserByPhone(String phone);
}