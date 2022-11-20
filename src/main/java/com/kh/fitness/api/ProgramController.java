package com.kh.fitness.api;

import com.kh.fitness.dto.trainingProgram.ProgramCreateDto;
import com.kh.fitness.dto.trainingProgram.ProgramEditDto;
import com.kh.fitness.dto.trainingProgram.ProgramReadDto;
import com.kh.fitness.dto.trainingProgram.ProgramReadWithSubProgramsDto;
import com.kh.fitness.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping(API_V1 + "/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @GetMapping("/{id}")
    public Optional<ProgramReadWithSubProgramsDto> findById(@PathVariable Long id) {
        return programService.findById(id);
    }

    @GetMapping
    public List<ProgramReadWithSubProgramsDto> findAllByGymId(@RequestParam Long gymId) {
        return programService.findAllByGymId(gymId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(CREATED)
    public Optional<ProgramReadDto> update(@PathVariable Long id, @RequestBody ProgramEditDto program) {
        return programService.update(id, program);
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(CREATED)
    public ProgramReadDto create(ProgramCreateDto program) {
        return programService.create(program);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(programService.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return programService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PutMapping("/{id}/avatar")
    @ResponseStatus(CREATED)
    public ProgramReadDto updateAvatar(@PathVariable Long id, @RequestParam MultipartFile image) {
        return programService.updateAvatar(id, image);
    }

    @DeleteMapping("/{id}/avatar")
    @ResponseStatus(NO_CONTENT)
    public void deleteAvatar(@PathVariable Long id) {
        if (Boolean.FALSE.equals(programService.removeAvatar(id))) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}