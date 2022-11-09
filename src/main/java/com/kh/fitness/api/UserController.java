package com.kh.fitness.api;

import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.service.RoleService;
import com.kh.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1 + "/users")
@Validated
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

//    @GetMapping("/{phone}")
//    public Optional<UserReadDto> getUserByUsername(@PathVariable String phone) {
//        return userService.findByUsername(phone);
//    }

    @GetMapping("/{id}")
    public Optional<UserReadDto> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserReadDto> getAll() {
        return userService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody UserCreateDto user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id,
                              @RequestBody UserCreateDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}