package com.kh.fitness.service;

import com.kh.fitness.dto.account.AccountChangePasswordDto;
import com.kh.fitness.dto.account.AccountEditDto;
import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserCreatedDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.dto.user.UserRegisterDto;
import com.kh.fitness.entity.Roles;
import com.kh.fitness.entity.User;
import com.kh.fitness.exception.PasswordMatchException;
import com.kh.fitness.exception.PhoneAlreadyExistException;
import com.kh.fitness.exception.UserNotFoundException;
import com.kh.fitness.mapper.account.AccountEditMapper;
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

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final RoleService roleService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserReadMapper userReadMapper;
    private final UserRegisterMapper userRegisterMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserCreatedDtoMapper userCreatedDtoMapper;
    private final AccountEditMapper accountEditMapper;

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
    public UserCreatedDto register(@Valid UserRegisterDto userDto) {
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
            log.error("Пользователь с username {} не найден", authentication.getName());
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
    public UserReadDto create(@Valid UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::toEntity)
                .map(this::setEncodedPassword)
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::toDto)
                .orElseThrow();
    }

    @Override
    @Transactional
    public Optional<UserReadDto> updateAccount(@Valid final AccountEditDto dto) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByPhone(authentication.getName())
                .map(user -> {
                    isNotExistByEmailOrPhoneElseThrow(user.getId(), dto);
                    return accountEditMapper.updateUser(dto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::toDto);
    }

    private void isNotExistByEmailOrPhoneElseThrow(Long id, AccountEditDto dto) {
        var phone = dto.getPhone();
        var email = dto.getEmail();
        checkExistByEmail(id, email);
        checkExistByPhone(id, phone);
    }

    private void checkExistByEmail(Long id, String email) {
        final String EMAIL_ALREADY_EXIST_MESSAGE = "Пользователь c id: {} c email: {} уже существует";
        userRepository.findByEmailIgnoreCase(email)
                .map(User::getId)
                .filter(userId -> !Objects.equals(userId, id))
                .map(userId -> {
                    log.error(EMAIL_ALREADY_EXIST_MESSAGE, userId, email);
                    throw new PhoneAlreadyExistException(email);
                });
    }

    private void checkExistByPhone(Long id, String phone) {
        final String PHONE_ALREADY_EXIST_MESSAGE = "Пользователь c id: {} c телефоном: {} уже существует";
        userRepository.findByPhone(phone)
                .map(User::getId)
                .filter(userId -> !Objects.equals(userId, id))
                .map(userId -> {
                    log.error(PHONE_ALREADY_EXIST_MESSAGE, userId, phone);
                    throw new PhoneAlreadyExistException(phone);
                });
    }

    @Transactional
    public Optional<UserReadDto> updateWithoutPassword(Long id, AccountEditDto dto) {
        return userRepository.findById(id)
                .map(user -> {
                    isNotExistByEmailOrPhoneElseThrow(user.getId(), dto);
                    return accountEditMapper.updateUser(dto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::toDto);
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, AccountEditDto dto) {
        return userRepository.findById(id)
                .map(user -> {
                    isNotExistByEmailOrPhoneElseThrow(user.getId(), dto);
                    return accountEditMapper.updateUser(dto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::toDto);
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
                .map(userReadMapper::toDto)
                .toList();
    }

    public List<UserReadDto> findAllWithRoleName(String name) {
        return userRepository.findAllWithRoleName(name).stream()
                .map(userReadMapper::toDto)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::toDto);
    }

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
                .orElseThrow(() -> new UsernameNotFoundException("Failed load user: " + username));
    }
}