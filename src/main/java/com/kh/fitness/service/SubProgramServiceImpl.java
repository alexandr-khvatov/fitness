package com.kh.fitness.service;

import com.kh.fitness.dto.subtraining_program.SubProgramCreateDto;
import com.kh.fitness.dto.subtraining_program.SubProgramEditDto;
import com.kh.fitness.dto.subtraining_program.SubProgramReadDto;
import com.kh.fitness.entity.SubTrainingProgram;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.subtraining_program.SubProgramCreateMapper;
import com.kh.fitness.mapper.subtraining_program.SubProgramEditMapper;
import com.kh.fitness.mapper.subtraining_program.SubProgramReadMapper;
import com.kh.fitness.repository.SubProgramRepository;
import com.kh.fitness.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class SubProgramServiceImpl {
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
                .flatMap(imageService::get);
    }

    @Transactional
    public SubProgramReadDto create(@Valid SubProgramCreateDto subProgram) {
        return Optional.of(subProgram)
                .map(dto -> {
                    var imageName = imageService.upload(dto.getImage());
                    var entity = subProgramCreateMapper.toEntity(dto);
                    entity.setImage(imageName);
                    return entity;
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
        var imageName = imageService.upload(image);
        entity.setImage(imageName);
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
            return imageService.remove(imagePath);
        }
        return false;
    }
}