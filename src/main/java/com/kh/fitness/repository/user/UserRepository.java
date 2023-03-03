package com.kh.fitness.repository.user;

import com.kh.fitness.entity.user.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User> {

    @Query("""
            SELECT u
            FROM User u 
            JOIN fetch u.roles ur 
            WHERE ur.name=upper(:name)
            """)
    List<User> findAllWithRoleName(@Param("name") String name);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhone(String phone);

    boolean existsUserByEmail(String email);

    boolean existsUserByPhone(String phone);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Page<User> findAll(Predicate predicate, Pageable pageable);
}