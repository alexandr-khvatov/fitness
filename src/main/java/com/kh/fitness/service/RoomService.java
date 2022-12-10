package com.kh.fitness.service;

import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.dto.room.RoomEditDto;
import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.mapper.room.RoomCreateMapper;
import com.kh.fitness.mapper.room.RoomEditMapper;
import com.kh.fitness.mapper.room.RoomReadMapper;
import com.kh.fitness.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomReadMapper roomReadMapper;
    private final RoomCreateMapper roomCreateMapper;
    private final RoomEditMapper roomEditMapper;

    public Optional<RoomReadDto> findById(Long id) {
        return roomRepository.findById(id)
                .map(roomReadMapper::map);
    }

    public List<RoomReadDto> findAllByGymId(Long gymId) {
        return roomRepository.findAllByGymId(gymId).stream()
                .map(roomReadMapper::map)
                .toList();
    }

    public RoomReadDto create(RoomCreateDto room) {
        return Optional.of(room)
                .map(roomCreateMapper::map)
                .map(roomRepository::saveAndFlush)
                .map(roomReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<RoomReadDto> update(Long id, @Valid RoomEditDto room) {
        return roomRepository.findById(id)
                .map(entity -> roomEditMapper.map(room, entity))
                .map(roomRepository::saveAndFlush)
                .map(roomReadMapper::map);
    }

    public Boolean delete(Long id) {
        return roomRepository.findById(id)
                .map(entity -> {
                    roomRepository.delete(entity);
                    roomRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}