package com.kh.fitness.service;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.exception.EmailAlreadyExistException;
import com.kh.fitness.exception.PhoneAlreadyExistException;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.coach.CoachCreateMapper;
import com.kh.fitness.mapper.coach.CoachEditMapper;
import com.kh.fitness.mapper.coach.CoachReadDtoMapper;
import com.kh.fitness.repository.CoachRepository;
import com.kh.fitness.validation.sequence.DefaultAndNotExistComplete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CoachService {
    private static final String EMAIL_ALREADY_EXIST_MESSAGE = "Тренер c id: {} c email: {} уже существует";
    private static final String PHONE_ALREADY_EXIST_MESSAGE = "Тренер c id: {} c телефоном: {} уже существует";

    private final ImageService imageService;

    private final CoachRepository coachRepository;
    private final CoachEditMapper coachEditMapper;
    private final CoachCreateMapper coachCreateMapper;
    private final CoachReadDtoMapper coachReadDtoMapper;

    public Optional<CoachReadDto> findById(Long id) {
        return coachRepository.findById(id).map(coachReadDtoMapper::toDto);
    }

    public List<CoachReadDto> findAllByGymId(Long gymId) {
        return coachRepository.findAllByGymId(gymId).stream()
                .map(coachReadDtoMapper::toDto)
                .toList();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return coachRepository.findById(id)
                .map(Coach::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    @Validated(DefaultAndNotExistComplete.class)
    public CoachReadDto create(@Valid CoachCreateDto coach) {
        return Optional.of(coach)
                .map(dto -> {
                    var imageName = imageService.upload(dto.getImage());
                    Coach map = coachCreateMapper.toEntity(dto);
                    imageName.ifPresent(map::setImage);
                    return map;
                })
                .map(coachRepository::saveAndFlush)
                .map(coachReadDtoMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<CoachReadDto> update(Long id, @Valid CoachEditDto coach) {
        return coachRepository.findById(id)
                .map(entity -> {
                    var phone = coach.getPhone();
                    var email = coach.getEmail();
                    // check the existence of other coach with this phone
                    coachRepository.findByPhone(phone)
                            .map(Coach::getId)
                            .filter(coachId -> !Objects.equals(coachId, entity.getId()))
                            .map(coachId -> {
                                log.error(PHONE_ALREADY_EXIST_MESSAGE, coachId, phone);
                                throw new PhoneAlreadyExistException(phone);
                            });
                    // check the existence of other coach with this email
                    coachRepository.findByEmailIgnoreCase(email)
                            .map(Coach::getId)
                            .filter(coachId -> !Objects.equals(coachId, entity.getId()))
                            .map(coachId -> {
                                log.error(EMAIL_ALREADY_EXIST_MESSAGE, coachId, email);
                                throw new EmailAlreadyExistException(email);
                            });

                    return coachEditMapper.updateCoach(coach, entity);
                })
                .map(coachRepository::saveAndFlush)
                .map(coachReadDtoMapper::toDto);
    }

    @Transactional
    public CoachReadDto updateAvatar(Long id, MultipartFile image) {
        var entity = coachRepository.findById(id).orElseThrow();
        var imageForRemoval = entity.getImage();
        var imageName = imageService.upload(image);
        imageName.ifPresent(entity::setImage);
        coachRepository.saveAndFlush(entity);
        removeImage(imageForRemoval);
        return coachReadDtoMapper.toDto(entity);
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

        try {
            return coachRepository.findById(id)
                    .map(entity -> {
                        coachRepository.delete(entity);
                        coachRepository.flush();
                        return true;
                    })
                    .orElse(false);
        } catch (Exception e) {
            throw new UnableToDeleteObjectContainsNestedObjects("Не возможно удалить, тренер закреплен за тренеровкой");
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