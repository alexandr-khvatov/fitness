package com.kh.fitness.service;

import com.kh.fitness.dto.subTrainingProgram.SubProgramCreateDto;
import com.kh.fitness.dto.subTrainingProgram.SubProgramEditDto;
import com.kh.fitness.dto.subTrainingProgram.SubProgramReadDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.subTrainingProgram.SubProgramCreateMapper;
import com.kh.fitness.mapper.subTrainingProgram.SubProgramEditMapper;
import com.kh.fitness.mapper.subTrainingProgram.SubProgramReadMapper;
import com.kh.fitness.repository.SubProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class SubProgramService {
    private final ImageService imageService;

    private final SubProgramRepository subProgramRepository;

    private final SubProgramEditMapper subProgramEditMapper;
    private final SubProgramCreateMapper subProgramCreateMapper;
    private final SubProgramReadMapper subProgramReadMapper;

    public Optional<SubProgramReadDto> findById(Long id) {
        return subProgramRepository.findById(id).map(subProgramReadMapper::map);
    }

    public List<SubProgramReadDto> findAll() {
        return subProgramRepository.findAll().stream()
                .map(subProgramReadMapper::map).toList();
    }

    public List<SubProgramReadDto> findAllByProgramId(Long programId) {
        return subProgramRepository.findAllByTrainingProgramId(programId).stream()
                .map(subProgramReadMapper::map).toList();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return subProgramRepository.findById(id)
                .map(SubTrainingProgram::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    @Transactional
    public SubProgramReadDto create(@Valid SubProgramCreateDto subProgram) {
        return Optional.of(subProgram)
                .map(dto -> {
                    var imageName = imageService.uploadImage(dto.getImage());
                    SubTrainingProgram map = subProgramCreateMapper.toEntity(dto);
                    imageName.ifPresent(map::setImage);
                    return map;
                })
                .map(subProgramRepository::saveAndFlush)
                .map(subProgramReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<SubProgramReadDto> update(Long id, @Valid SubProgramEditDto subProgram) {
        return subProgramRepository.findById(id)
                .map(entity -> subProgramEditMapper.updateEntity(subProgram, entity))
                .map(subProgramRepository::saveAndFlush)
                .map(subProgramReadMapper::map);
    }

    @Transactional
    public SubProgramReadDto updateAvatar(Long id, MultipartFile image) {
        var entity = subProgramRepository.findById(id).orElseThrow();
        var imageForRemoval = entity.getImage();
        var imageName = imageService.uploadImage(image);
        imageName.ifPresent(entity::setImage);
        subProgramRepository.saveAndFlush(entity);
        removeImage(imageForRemoval);
        return subProgramReadMapper.map(entity);
    }

    @Transactional
    public boolean removeAvatar(Long id) {
        var entity = subProgramRepository.findById(id).orElseThrow();
        entity.setImage(null);
        subProgramRepository.saveAndFlush(entity);
        return removeImage(entity.getImage());
    }

    @Transactional
    public Boolean delete(Long id) {
        try {
            return subProgramRepository.findById(id)
                    .map(entity -> {
                        subProgramRepository.delete(entity);
                        subProgramRepository.flush();
                        return true;
                    })
                    .orElse(false);
        } catch (Exception e) {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно удалить, программа закреплена за тренеровкой");
        }
    }

    /**
     * Calls a method to remove an image
     *
     * @param imagePath the path to the file to delete
     * @return {@code true} if image deleted successfully; <br/>
     * {@code false}  image {@code imagePath} is null or the file could not be deleted because it did not exist
     */
    private boolean removeImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            return imageService.removeImage(imagePath);
        }
        return false;
    }
}