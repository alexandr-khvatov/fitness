package com.kh.fitness.repository;

import com.kh.fitness.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhone(String phone);

    boolean existsUserById(Long id);

    boolean existsUserByEmail(String email);

    boolean existsUserByPhone(String phone);

}