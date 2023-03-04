package com.kh.fitness.api;

import com.kh.fitness.dto.PageResponse;
import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserEditDto;
import com.kh.fitness.dto.user.UserFilter;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    private static final String ERROR_MSG_NOT_FOUND = "User with id %s not found";

    @GetMapping(API_V1 + "/users/username/{username}")
    public UserReadDto findByUsername(@PathVariable String username) {
        return userService.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format("User with username %s not found", username))
        );
    }

    @GetMapping(API_V1 + "/users/{id}")
    public UserReadDto findById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @GetMapping(API_V1 + "/gyms/{gymId}/users")
    public PageResponse<UserReadDto> findAllByGymIdAndFilter(
            @PathVariable Long gymId,
            UserFilter filter,
            Pageable pageable
    ) {
        return PageResponse.of(userService.findAllByGymIdAndFilter(gymId, filter, pageable));
    }

    @GetMapping(API_V1 + "/users")
    public PageResponse<UserReadDto> findAllByFilter(UserFilter filter, Pageable pageable) {
        return PageResponse.of(userService.findAllByFilter(filter, pageable));
    }

    @PostMapping(path = API_V1 + "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody UserCreateDto user) {
        return userService.create(user);
    }

    @PutMapping(API_V1 + "/users/{id}")
    public UserReadDto update(@PathVariable("id") Long id,
                              @RequestBody UserEditDto user) {
        return userService.update(id, user).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @DeleteMapping(API_V1 + "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (Boolean.FALSE.equals(userService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id));
        }
    }

    @GetMapping(value = API_V1 + "/users/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PutMapping(API_V1 + "/users/{id}/avatar")
    public String updateAvatar(@PathVariable Long id, @RequestParam MultipartFile image) {
        return userService.updateAvatar(id, image);
    }

    @DeleteMapping(API_V1 + "/users/{id}/avatar")
    @ResponseStatus(NO_CONTENT)
    public void deleteAvatar(@PathVariable Long id) {
        if (Boolean.FALSE.equals(userService.removeAvatar(id))) {
            throw new ResponseStatusException(NOT_FOUND, "File not exist");
        }
    }
}