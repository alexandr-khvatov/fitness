package com.kh.fitness.api;

import com.kh.fitness.dto.subTrainingProgram.SubProgramCreateDto;
import com.kh.fitness.dto.subTrainingProgram.SubProgramEditDto;
import com.kh.fitness.dto.subTrainingProgram.SubProgramReadDto;
import com.kh.fitness.service.SubProgramServiceImpl;
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
@RequestMapping(API_V1 + "/sub-programs")
@RequiredArgsConstructor
public class SubProgramController {
    private final SubProgramServiceImpl subProgramServiceImpl;

    @GetMapping("/{id}")
    public Optional<SubProgramReadDto> findById(@PathVariable Long id) {
        return subProgramServiceImpl.findById(id);
    }

    @GetMapping("/all")
    public List<SubProgramReadDto> findAll() {
        return subProgramServiceImpl.findAll();
    }

    @GetMapping
    public List<SubProgramReadDto> findAllByProgramId(@RequestParam Long programId) {
        return subProgramServiceImpl.findAllByProgramId(programId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(CREATED)
    public Optional<SubProgramReadDto> update(@PathVariable Long id, @RequestBody SubProgramEditDto subProgram) {
        return subProgramServiceImpl.update(id, subProgram);
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(CREATED)
    public SubProgramReadDto create(SubProgramCreateDto subProgram) {
        return subProgramServiceImpl.create(subProgram);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (Boolean.FALSE.equals(subProgramServiceImpl.delete(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return subProgramServiceImpl.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PutMapping("/{id}/avatar")
    @ResponseStatus(CREATED)
    public SubProgramReadDto updateAvatar(@PathVariable Long id, @RequestParam MultipartFile image) {
        return subProgramServiceImpl.updateAvatar(id, image);
    }

    @DeleteMapping("/{id}/avatar")
    @ResponseStatus(NO_CONTENT)
    public void deleteAvatar(@PathVariable Long id) {
        if (Boolean.FALSE.equals(subProgramServiceImpl.removeAvatar(id))) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}