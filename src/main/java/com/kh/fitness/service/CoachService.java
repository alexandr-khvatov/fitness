package com.kh.fitness.service;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.mapper.coach.CoachCreateMapper;
import com.kh.fitness.mapper.coach.CoachEditMapper;
import com.kh.fitness.mapper.coach.CoachReadDtoMapper;
import com.kh.fitness.repository.CoachRepository;
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
public class CoachService {
    private final ImageService imageService;

    private final CoachRepository coachRepository;
    private final CoachEditMapper coachEditMapper;
    private final CoachCreateMapper coachCreateMapper;
    private final CoachReadDtoMapper coachReadDtoMapper;

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    public List<CoachReadDto> findAllByGymId(Long gymId) {
        return coachRepository.findAllByGymId(gymId).stream()
                .map(coachReadDtoMapper::map).toList();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return coachRepository.findById(id)
                .map(Coach::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    @Transactional
    public CoachReadDto create(@Valid CoachCreateDto coach) {
        return Optional.of(coach)
                .map(dto -> {
                    var imageName = uploadImage(dto.getImage());
                    Coach map = coachCreateMapper.map(dto);
                    imageName.ifPresent(map::setImage);
                    return map;
                })
                .map(coachRepository::saveAndFlush)
                .map(coachReadDtoMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CoachReadDto> update(Long id, CoachEditDto coach) {
        return coachRepository.findById(id)
                .map(entity -> coachEditMapper.map(coach, entity))
                .map(coachRepository::saveAndFlush)
                .map(coachReadDtoMapper::map);
    }

    @Transactional
    public CoachReadDto updateAvatar(Long id, MultipartFile image) {
        var entity = coachRepository.findById(id).orElseThrow();
        var imageName = uploadImage(image);
        imageName.ifPresent(entity::setImage);
        coachRepository.saveAndFlush(entity);
        removeImage(entity.getImage());
        return coachReadDtoMapper.map(entity);
    }

    @Transactional
    public boolean removeAvatar(Long id) {
        var entity = coachRepository.findById(id).orElseThrow();
        entity.setImage(null);
        coachRepository.saveAndFlush(entity);
        return removeImage(entity.getImage());
    }

    @Transactional
    public Boolean delete(Long id) {
        return coachRepository.findById(id)
                .map(entity -> {
                    coachRepository.delete(entity);
                    coachRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @SneakyThrows
    private Optional<String> uploadImage(MultipartFile image) {
        final String IMAGE_NAME = UUID.randomUUID().toString();
        if (image != null && !image.isEmpty()) {
            imageService.upload(IMAGE_NAME, image.getInputStream());
            return Optional.of(IMAGE_NAME);
        }
        return Optional.empty();
    }

    private boolean removeImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            return imageService.removeImage(imagePath);
        }
        return false;
    }
}