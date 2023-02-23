package com.kh.fitness.service;

import com.kh.fitness.dto.trainingProgram.ProgramCreateDto;
import com.kh.fitness.dto.trainingProgram.ProgramEditDto;
import com.kh.fitness.dto.trainingProgram.ProgramReadDto;
import com.kh.fitness.dto.trainingProgram.ProgramReadWithSubProgramsDto;
import com.kh.fitness.entity.TrainingProgram;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.trainingProgram.ProgramCreateMapper;
import com.kh.fitness.mapper.trainingProgram.ProgramEditMapper;
import com.kh.fitness.mapper.trainingProgram.ProgramReadMapper;
import com.kh.fitness.repository.ProgramRepository;
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
public class ProgramServiceImpl {
    private final ImageService imageService;

    private final ProgramRepository programRepository;

    private final ProgramEditMapper programEditMapper;
    private final ProgramCreateMapper programCreateMapper;
    private final ProgramReadMapper programReadMapper;

    public Optional<ProgramReadWithSubProgramsDto> findById(Long id) {
        return programRepository.findById(id).map(programReadMapper::toDtoWithSubPrograms);
    }

    public List<ProgramReadWithSubProgramsDto> findAllByGymId(Long gymId) {
        return programRepository.findAllByGymId(gymId).stream()
                .map(programReadMapper::toDtoWithSubPrograms).toList();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return programRepository.findById(id)
                .map(TrainingProgram::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public ProgramReadDto create(@Valid ProgramCreateDto program) {
        return Optional.of(program)
                .map(dto -> {
                    var imageName = imageService.upload(dto.getImage());
                    TrainingProgram map = programCreateMapper.toEntity(dto);
                    imageName.ifPresent(map::setImage);
                    return map;
                })
                .map(programRepository::saveAndFlush)
                .map(programReadMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProgramReadDto> update(Long id, @Valid ProgramEditDto program) {
        return programRepository.findById(id)
                .map(entity -> {

                    return programEditMapper.updateEntity(program, entity);
                })
                .map(programRepository::saveAndFlush)
                .map(programReadMapper::toDto);
    }

    @Transactional
    public ProgramReadDto updateAvatar(Long id, MultipartFile image) {
        var entity = programRepository.findById(id).orElseThrow();
        var imageForRemoval = entity.getImage();
        var imageName = imageService.upload(image);
        imageName.ifPresent(entity::setImage);
        programRepository.saveAndFlush(entity);
        removeImage(imageForRemoval);
        return programReadMapper.toDto(entity);
    }

    @Transactional
    public boolean removeAvatar(Long id) {
        var entity = programRepository.findById(id).orElseThrow();
        entity.setImage(null);
        programRepository.saveAndFlush(entity);
        return removeImage(entity.getImage());
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