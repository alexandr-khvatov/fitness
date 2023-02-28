package com.kh.fitness.repository;

import com.kh.fitness.entity.user.Role;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends Repository<Role, Long> {

    List<Role> findAll();

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);
}
