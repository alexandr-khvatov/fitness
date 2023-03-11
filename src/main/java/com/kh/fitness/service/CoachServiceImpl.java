package com.kh.fitness.service;

import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.dto.coach.CoachFilter;
import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.entity.Coach;
import com.kh.fitness.exception.PhoneAlreadyExistException;
import com.kh.fitness.exception.UnableToDeleteObjectContainsNestedObjects;
import com.kh.fitness.mapper.coach.CoachCreateMapper;
import com.kh.fitness.mapper.coach.CoachEditMapper;
import com.kh.fitness.mapper.coach.CoachReadDtoMapper;
import com.kh.fitness.querydsl.QPredicates;
import com.kh.fitness.repository.CoachRepository;
import com.kh.fitness.service.image.ImageService;
import com.kh.fitness.validation.sequence.DefaultAndNotExistComplete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static com.kh.fitness.entity.QCoach.coach;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CoachServiceImpl implements AvatarService {

    private final ImageService imageService;
    private final CoachRepository coachRepository;
    private final CoachEditMapper coachEditMapper;
    private final CoachCreateMapper coachCreateMapper;
    private final CoachReadDtoMapper coachReadDtoMapper;

    public static final String EXC_MSG_NOT_FOUND = "Coach with id %s not found";

    public Optional<CoachReadDto> findById(Long id) {
        return coachRepository.findById(id).map(coachReadDtoMapper::toDto);
    }

    public Page<CoachReadDto> findAllByFilter(CoachFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), coach.firstname::containsIgnoreCase)
                .add(filter.patronymic(), coach.patronymic::containsIgnoreCase)
                .add(filter.lastname(), coach.lastname::containsIgnoreCase)
                .add(filter.phone(), coach.phone::containsIgnoreCase)
                .add(filter.email(), coach.email::containsIgnoreCase)
                .add(filter.birthDate(), coach.birthDate::before)
                .build();

        return coachRepository.findAll(predicate, pageable)
                .map(coachReadDtoMapper::toDto);
    }

    public Page<CoachReadDto> findAllByGymIdAndFilter(Long gymId, CoachFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(gymId, coach.gym.id::eq)
                .add(filter.firstname(), coach.firstname::containsIgnoreCase)
                .add(filter.patronymic(), coach.patronymic::containsIgnoreCase)
                .add(filter.lastname(), coach.lastname::containsIgnoreCase)
                .add(filter.phone(), coach.phone::containsIgnoreCase)
                .add(filter.email(), coach.email::containsIgnoreCase)
                .add(filter.birthDate(), coach.birthDate::before)
                .build();

        return coachRepository.findAll(predicate, pageable)
                .map(coachReadDtoMapper::toDto);
    }

    @Transactional
    @Validated(DefaultAndNotExistComplete.class)
    public CoachReadDto create(@Valid CoachCreateDto coach) {
        return Optional.of(coach)
                .map(dto -> {
                    var imageName = imageService.upload(dto.getImage());
                    Coach c = coachCreateMapper.toEntity(dto);
                    c.setImage(imageName);
                    return c;
                })
                .map(coachRepository::saveAndFlush)
                .map(coachReadDtoMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<CoachReadDto> update(Long id, @Valid CoachEditDto coach) {
        return coachRepository.findById(id)
                .map(entity -> {
                    checkExistByPhone(id, coach.getPhone());
                    checkExistByEmail(id, coach.getEmail());
                    return coachEditMapper.updateCoach(coach, entity);
                })
                .map(coachRepository::saveAndFlush)
                .map(coachReadDtoMapper::toDto);
    }

    private void checkExistByEmail(Long id, String email) {
        final String EMAIL_ALREADY_EXIST_MESSAGE = "Coach with id {} and email {} already exist";
        coachRepository.findByEmailIgnoreCase(email)
                .map(Coach::getId)
                .filter(coachId -> !Objects.equals(coachId, id))
                .map(coachId -> {
                    log.error(EMAIL_ALREADY_EXIST_MESSAGE, coachId, email);
                    throw new PhoneAlreadyExistException(email);
                });
    }

    private void checkExistByPhone(Long id, String phone) {
        final String PHONE_ALREADY_EXIST_MESSAGE = "User with id {} and phone {} already exist";
        coachRepository.findByPhone(phone)
                .map(Coach::getId)
                .filter(coachId -> !Objects.equals(coachId, id))
                .map(coachId -> {
                    log.error(PHONE_ALREADY_EXIST_MESSAGE, coachId, phone);
                    throw new PhoneAlreadyExistException(phone);
                });
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
            throw new UnableToDeleteObjectContainsNestedObjects("Unable to delete, trainer assigned to training");
        }
    }

    @Override
    public Optional<byte[]> findAvatar(Long id) {
        return coachRepository.findById(id)
                .map(Coach::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    @Transactional
    public String updateAvatar(Long id, MultipartFile image) {
        var entity = coachRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(format(EXC_MSG_NOT_FOUND, id)));

        var imageForRemoval = entity.getImage();
        var imageName = imageService.upload(image);
        entity.setImage(imageName);
        coachRepository.saveAndFlush(entity);
        imageService.remove(imageForRemoval);

        return imageName;
    }

    @Override
    @Transactional
    public boolean removeAvatar(Long id) {
        var entity = coachRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(format(EXC_MSG_NOT_FOUND, id)));
        var removeAvatar = entity.getImage();
        if (removeAvatar == null || removeAvatar.isEmpty()) {
            return true;
        }
        entity.setImage(null);
        coachRepository.saveAndFlush(entity);
        return imageService.remove(removeAvatar);
    }
}