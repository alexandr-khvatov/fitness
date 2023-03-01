package com.kh.fitness.api;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.service.CoachServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping(API_V1 + "/coaches")
@RequiredArgsConstructor
public class CoachController {
    private final CoachServiceImpl coachService;

    private static final String ERROR_MSG_NOT_FOUND = "Coach with id %s not found";

    @GetMapping("/{id}")
    public CoachReadDto findById(@PathVariable Long id) {
        return coachService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @GetMapping
    public List<CoachReadDto> findAllByGymId(@RequestParam Long gymId) {
        return coachService.findAllByGymId(gymId);
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(CREATED)
    public CoachReadDto create(CoachCreateDto coach) {
        return coachService.create(coach);
    }

    @PutMapping("/{id}")
    public CoachReadDto update(@PathVariable Long id, @RequestBody CoachEditDto coach) {
        return coachService.update(id, coach).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(coachService.delete(id))) {
            throw new ResponseStatusException(NOT_FOUND, format(ERROR_MSG_NOT_FOUND, id));
        }
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return coachService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PutMapping("/{id}/avatar")
    public String updateAvatar(@PathVariable Long id, @RequestParam MultipartFile image) {
        return coachService.updateAvatar(id, image);
    }

    @DeleteMapping("/{id}/avatar")
    @ResponseStatus(NO_CONTENT)
    public void deleteAvatar(@PathVariable Long id) {
        if (Boolean.FALSE.equals(coachService.removeAvatar(id))) {
            throw new ResponseStatusException(NOT_FOUND, "File not exist");
        }
    }
}