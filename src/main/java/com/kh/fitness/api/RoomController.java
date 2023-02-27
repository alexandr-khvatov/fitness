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

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(API_V1)
@RequiredArgsConstructor
public class RoomController {
    private static final String ROOM_PREFIX = "/rooms";
    private final RoomServiceImpl roomServiceImpl;

    public static final String ERROR_MSG_NOT_FOUND = "Room with id %s not found";

    @GetMapping(ROOM_PREFIX + "/{id}")
    public RoomReadDto findById(@PathVariable Long id) {
        return roomServiceImpl.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @RequestMapping("/gyms/{gymId}/rooms")
    @GetMapping
    public List<RoomReadDto> findAllByGymId(@PathVariable Long gymId) {
        List<RoomReadDto> rooms = roomServiceImpl.findAllByGymId(gymId);
        if (rooms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rooms with gymId " + gymId + " not found");
        }
        return rooms;
    }

    @PostMapping(ROOM_PREFIX)
    @ResponseStatus(CREATED)
    public RoomReadDto create(@RequestBody RoomCreateDto room) {
        return roomServiceImpl.create(room);
    }

    @PutMapping(ROOM_PREFIX + "/{id}")
    public RoomReadDto update(@PathVariable Long id, @RequestBody RoomEditDto room) {
        return roomServiceImpl.update(id, room).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @DeleteMapping(ROOM_PREFIX + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(roomServiceImpl.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id));
        }
    }
}