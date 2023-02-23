package com.kh.fitness.api;

import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.dto.room.RoomEditDto;
import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(API_V1 + "/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomServiceImpl roomServiceImpl;

    @GetMapping("/{id}")
    public Optional<RoomReadDto> findById(@PathVariable Long id) {
        return roomServiceImpl.findById(id);
    }

    @GetMapping
    public List<RoomReadDto> findAllByGymId(@RequestParam Long gymId) {
        return roomServiceImpl.findAllByGymId(gymId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public RoomReadDto create(@RequestBody RoomCreateDto room) {
        return roomServiceImpl.create(room);
    }

    @PutMapping("/{id}")
    public Optional<RoomReadDto> update(@PathVariable Long id, @RequestBody RoomEditDto room) {
        return roomServiceImpl.update(id, room);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(roomServiceImpl.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}