package com.kh.fitness.service.user;

import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.user.*;
import com.kh.fitness.entity.user.Roles;
import com.kh.fitness.entity.user.User;
import com.kh.fitness.exception.PasswordMatchException;
import com.kh.fitness.exception.PhoneAlreadyExistException;
import com.kh.fitness.exception.UserNotFoundException;
import com.kh.fitness.mapper.user.*;
import com.kh.fitness.querydsl.QPredicates;
import com.kh.fitness.repository.user.UserRepository;
import com.kh.fitness.service.RoleServiceImpl;
import com.kh.fitness.service.image.ImageService;
import com.kh.fitness.validation.sequence.DefaultAndNotExistComplete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

import static com.kh.fitness.entity.user.QUser.user;
import static java.lang.String.format;

@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final RoleServiceImpl roleService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserReadMapper userReadMapper;
    private final UserRegisterMapper userRegisterMapper;
    private final UserCreateMapper userCreateMapper;
    private final UserCreatedDtoMapper userCreatedDtoMapper;
    private final UserEditMapper userEditMapper;

    public static final String EXC_MSG_NOT_FOUND = "User with id %s not found";
    public static final String EXC_MSG_NOT_FOUND_BY_USERNAME = "User with username %s not found";
    public static final String LOG_MSG_NOT_FOUND_BY_USERNAME = "User with username {} not found";

    private User setEncodedPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    private User setDefaultRole(User user) {
        return roleService.findByName(Roles.CUSTOMER.name())
                .map(roles -> {
                    user.setRoles(Collections.singleton(roles));
                    return user;
                })
                .orElseThrow();
    }

    @Override
    @Transactional
    @Validated(DefaultAndNotExistComplete.class)
    public UserCreatedDto register(@Valid final UserRegisterDto userDto) {
        return Optional.of(userDto)
                .map(userRegisterMapper::toEntity)
                .map(this::setDefaultRole)
                .map(this::setEncodedPassword)
                .map(userRepository::saveAndFlush)
                .map(userCreatedDtoMapper::toDto)
                .orElseThrow();
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void changePassword(@Valid final AccountChangePasswordDto dto) {
        final String POSSIBLE_CURRENT_NOT_EQUAL_CURRENT_PWD_MESSAGE = "Неверный пароль,для изменения пароля необходимо ввести текущий пароль";
        final String CURRENT_EQUAL_NEW_PWD_MESSAGE = "Новый пароль совпадает со старым";
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.findByPhone(authentication.getName()).ifPresentOrElse(user -> {
            var currentPassword = user.getPassword();
            var possibleCurrentRawPassword = dto.getCurrentPassword();
            var newRawPassword = dto.getNewPassword();
            if (!passwordEncoder.matches(possibleCurrentRawPassword, currentPassword)) {
                throw new PasswordMatchException(POSSIBLE_CURRENT_NOT_EQUAL_CURRENT_PWD_MESSAGE);
            }
            if (passwordEncoder.matches(newRawPassword, currentPassword)) {
                throw new PasswordMatchException(CURRENT_EQUAL_NEW_PWD_MESSAGE);
            }
            user.setPassword(passwordEncoder.encode(newRawPassword));
            userRepository.saveAndFlush(user);
        }, () -> {
            log.error(LOG_MSG_NOT_FOUND_BY_USERNAME, authentication.getName());
            throw new UserNotFoundException(authentication.getName());
        });
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public boolean existsUserByPhone(String phone) {
        return userRepository.existsUserByPhone(phone);
    }

    @Override
    @Transactional
    @Validated(DefaultAndNotExistComplete.class)
    public UserReadDto create(final UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(userCreateMapper::toEntity)
                .map(this::setEncodedPassword)
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::toDto)
                .orElseThrow();
    }

    private void isNotExistByEmailOrPhoneElseThrow(@NotNull Long id, @NotNull String phone, @NotNull String email) {
        checkExistByEmail(id, email);
        checkExistByPhone(id, phone);
    }

    private void checkExistByEmail(Long id, String email) {
        final String EMAIL_ALREADY_EXIST_MESSAGE = "User with id {} and email {} already exist";
        userRepository.findByEmailIgnoreCase(email)
                .map(User::getId)
                .filter(userId -> !Objects.equals(userId, id))
                .map(userId -> {
                    log.error(EMAIL_ALREADY_EXIST_MESSAGE, userId, email);
                    throw new PhoneAlreadyExistException(email);
                });
    }

    private void checkExistByPhone(Long id, String phone) {
        final String PHONE_ALREADY_EXIST_MESSAGE = "User with id {} and phone {} already exist";
        userRepository.findByPhone(phone)
                .map(User::getId)
                .filter(userId -> !Objects.equals(userId, id))
                .map(userId -> {
                    log.error(PHONE_ALREADY_EXIST_MESSAGE, userId, phone);
                    throw new PhoneAlreadyExistException(phone);
                });
    }

    @Override
    @Transactional
    public Optional<UserReadDto> update(Long id, UserEditDto dto) {
        return userRepository.findById(id)
                .map(user -> {
                    isNotExistByEmailOrPhoneElseThrow(user.getId(), dto.getPhone(), dto.getEmail());
                    return userEditMapper.updateEntity(dto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::toDto);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Page<UserReadDto> findAllByFilter(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.patronymic(), user.patronymic::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.phone(), user.phone::containsIgnoreCase)
                .add(filter.email(), user.email::containsIgnoreCase)
                .add(filter.role(), user.roles.any().name::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::toDto);
    }

    @Override
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::toDto);
    }

    @Override
    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByPhone(username).map(userReadMapper::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByPhone(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                                user.getPhone(),
                                user.getPassword(),
                                user.getRoles()
                        )
                )
                .orElseThrow(() -> {
                    log.error(LOG_MSG_NOT_FOUND_BY_USERNAME, username);
                    throw new NoSuchElementException(format(EXC_MSG_NOT_FOUND_BY_USERNAME, username));
                });
    }

    @Override
    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    @Transactional
    public String updateAvatar(Long id, MultipartFile image) {
        var entity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(format(EXC_MSG_NOT_FOUND, id)));

        var imageForRemoval = entity.getImage();
        var imageName = imageService.upload(image);
        entity.setImage(imageName);
        userRepository.saveAndFlush(entity);
        imageService.remove(imageForRemoval);

        return imageName;
    }

    @Override
    @Transactional
    public boolean removeAvatar(Long id) {
        var entity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(format(EXC_MSG_NOT_FOUND, id)));
        var removeAvatar = entity.getImage();
        if (removeAvatar == null || removeAvatar.isEmpty()) {
            return true;
        }
        entity.setImage(null);
        userRepository.saveAndFlush(entity);
        return imageService.remove(removeAvatar);
    }
}