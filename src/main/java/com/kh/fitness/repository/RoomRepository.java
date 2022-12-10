package com.kh.fitness.repository;

import com.kh.fitness.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findById(Long Id);

    List<Room> findAllByGymId(Long gymId);
}