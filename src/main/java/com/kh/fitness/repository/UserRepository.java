package com.kh.fitness.repository;

import com.kh.fitness.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u " +
           "FROM User u JOIN fetch u.roles ur " +
           "WHERE ur.name=upper(:name)")
    List<User> findAllWithRoleName(@Param("name") String name);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhone(String phone);

    boolean existsUserById(Long id);

    boolean existsUserByEmail(String email);

    boolean existsUserByPhone(String phone);

}