package com.kh.fitness.service;

import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.Roles;
import com.kh.fitness.entity.User;
import com.kh.fitness.exception.EmailAlreadyExistException;
import com.kh.fitness.exception.PasswordMatchException;
import com.kh.fitness.exception.PhoneAlreadyExistException;
import com.kh.fitness.exception.UserNotFoundException;
import com.kh.fitness.mapper.account.AccountEditDtoMapper;
import com.kh.fitness.mapper.user.UserCreateEditMapper;
import com.kh.fitness.mapper.user.UserCreatedDtoMapper;
import com.kh.fitness.mapper.user.UserReadMapper;
import com.kh.fitness.mapper.user.UserRegisterMapper;
import com.kh.fitness.repository.UserRepository;
import com.kh.fitness.validation.sequence.DefaultAndNotExistComplete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class UserService implements UserDetailsService {
    private static final String POSSIBLE_CURRENT_NOT_EQUAL_CURRENT_PWD_MESSAGE = "Неверный пароль,для изменения пароля необходимо ввести текущий пароль";
    private static final String CURRENT_EQUAL_NEW_PWD_MESSAGE = "Новый пароль совпадает со старым";
    private static final String EMAIL_ALREADY_EXIST_MESSAGE = "Пользователь c id: {} c email: {} уже существует";
    private static final String PHONE_ALREADY_EXIST_MESSAGE = "Пользователь c id: {} c телефоном: {} уже существует";

    private final RoleService roleService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserReadMapper userReadMapper;
    private final UserRegisterMapper userRegisterMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserCreatedDtoMapper userCreatedDtoMapper;
    private final AccountEditDtoMapper accountEditDtoMapper;

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

    @Transactional
    @Validated(DefaultAndNotExistComplete.class)
    public UserCreatedDto register(@Valid UserRegisterDto userDto) {
        return Optional.of(userDto)
                .map(userRegisterMapper::map)
                .map(this::setDefaultRole)
                .map(this::setEncodedPassword)
                .map(userRepository::saveAndFlush)
                .map(userCreatedDtoMapper::map)
                .orElseThrow();
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void changePassword(@Valid final AccountChangePasswordDto dto) {
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
            log.error("Пользователь с username {} не найден", authentication.getName());
            throw new UserNotFoundException(authentication.getName());
        });
    }

    @Transactional
    @Validated(DefaultAndNotExistComplete.class)
    public UserReadDto create(@Valid UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(this::setEncodedPassword)
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    public boolean existsUserById(Long id) {
        return userRepository.existsUserById(id);
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public boolean existsUserByPhone(String phone) {
        return userRepository.existsUserByPhone(phone);
    }

    @Transactional
    public Optional<UserReadDto> updateAccount(@Valid final AccountEditDto dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var phone = dto.getPhone();
        var email = dto.getEmail();
        return userRepository.findByPhone(authentication.getName())
                .map(user -> {
                    // check the existence of other users with this phone
                    userRepository.findByPhone(phone)
                            .map(User::getId)
                            .filter(id -> !Objects.equals(id, user.getId()))
                            .map(id -> {
                                log.error(PHONE_ALREADY_EXIST_MESSAGE, id, phone);
                                throw new PhoneAlreadyExistException(phone);
                            });
                    // check the existence of other users with this email
                    userRepository.findByEmailIgnoreCase(email)
                            .map(User::getId)
                            .filter(id -> !Objects.equals(id, user.getId()))
                            .map(id -> {
                                log.error(EMAIL_ALREADY_EXIST_MESSAGE, id, email);
                                throw new EmailAlreadyExistException(email);
                            });

                    return accountEditDtoMapper.map(dto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateDto dto) {
//        return userRepository.findById(id)
//                .map(entity -> {
////                    uploadImage(userDto.getImage());
//                    return userCreateEditMapper.map(userDto, entity);
//                })
//                .map(userRepository::saveAndFlush)
//                .map(userReadMapper::map);
        var phone = dto.getPhone();
        var email = dto.getEmail();
        return userRepository.findById(id)
                .map(user -> {
                    // check the existence of other users with this phone
                    userRepository.findByPhone(phone)
                            .map(User::getId)
                            .filter(userId -> !Objects.equals(userId, user.getId()))
                            .map(userId -> {
                                log.error(PHONE_ALREADY_EXIST_MESSAGE, userId, phone);
                                throw new PhoneAlreadyExistException(phone);
                            });
                    // check the existence of other users with this email
                    userRepository.findByEmailIgnoreCase(email)
                            .map(User::getId)
                            .filter(userId -> !Objects.equals(userId, user.getId()))
                            .map(userId -> {
                                log.error(EMAIL_ALREADY_EXIST_MESSAGE, id, email);
                                throw new EmailAlreadyExistException(email);
                            });

                    return userCreateEditMapper.map(dto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                imageService.upload(image.getOriginalFilename(), image.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

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

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(x -> new UserReadMapper().map(x))
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::map);
    }

    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByPhone(username).map(userReadMapper::map);
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
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}