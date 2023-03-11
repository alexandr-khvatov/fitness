package com.kh.fitness.service;

import com.kh.fitness.dto.training_program.*;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.training_program.ProgramCreateMapper;
import com.kh.fitness.mapper.training_program.ProgramEditMapper;
import com.kh.fitness.mapper.training_program.ProgramReadMapper;
import com.kh.fitness.querydsl.QPredicates;
import com.kh.fitness.repository.ProgramRepository;
import com.kh.fitness.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.kh.fitness.entity.QTrainingProgram.trainingProgram;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class ProgramServiceImpl implements AvatarService {

    private final ImageService imageService;
    private final ProgramRepository programRepository;
    private final ProgramEditMapper programEditMapper;
    private final ProgramCreateMapper programCreateMapper;
    private final ProgramReadMapper programReadMapper;

    public static final String EXC_MSG_NOT_FOUND = "Program with id %s not found";

    public Optional<ProgramReadWithSubProgramsDto> findById(Long id) {
        return programRepository.findById(id).map(programReadMapper::toDtoWithSubPrograms);
    }

    private TrainingProgram getTraining(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(format(EXC_MSG_NOT_FOUND, id)));
    }

    public Page<ProgramReadWithSubProgramsDto> findAllByGymIdAndFilter(Long gymId, ProgramFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(gymId, trainingProgram.gym.id::eq)
                .add(filter.name(), trainingProgram.name::containsIgnoreCase)
                .add(filter.description(), trainingProgram.description::containsIgnoreCase)
                .build();

        return programRepository.findAll(predicate, pageable)
                .map(programReadMapper::toDtoWithSubPrograms);
    }

    @Transactional
    public ProgramReadDto create(@Valid ProgramCreateDto program) {
        return Optional.of(program)
                .map(dto -> {
                    var imageName = imageService.upload(dto.getImage());
                    var entity = programCreateMapper.toEntity(dto);
                    entity.setImage(imageName);
                    return entity;
                })
                .map(programRepository::saveAndFlush)
                .map(programReadMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProgramReadDto> update(Long id, @Valid ProgramEditDto program) {
        return programRepository.findById(id)
                .map(entity -> programEditMapper.updateEntity(program, entity))
                .map(programRepository::saveAndFlush)
                .map(programReadMapper::toDto);
    }

    @Transactional
    public Boolean delete(Long id) {
        try {
            return programRepository.findById(id)
                    .map(entity -> {
                        programRepository.delete(entity);
                        programRepository.flush();
                        return true;
                    })
                    .orElse(false);
        } catch (Exception e) {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно удалить, имеются вложенные тренеровки");
        }
    }

    @Override
    public Optional<byte[]> findAvatar(Long id) {
        return programRepository.findById(id)
                .map(TrainingProgram::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    @Transactional
    public String updateAvatar(Long id, MultipartFile image) {
        var entity = getTraining(id);

        var imageForRemoval = entity.getImage();
        var imageName = imageService.upload(image);
        entity.setImage(imageName);
        programRepository.saveAndFlush(entity);
        imageService.remove(imageForRemoval);

        return imageName;
    }

    @Override
    @Transactional
    public boolean removeAvatar(Long id) {
        var entity = getTraining(id);

        var removeAvatar = entity.getImage();
        if (removeAvatar == null || removeAvatar.isEmpty()) {
            return true;
        }
        entity.setImage(null);
        programRepository.saveAndFlush(entity);

        return imageService.remove(removeAvatar);
    }
}