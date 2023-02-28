package com.kh.fitness.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AvatarService {
    Optional<byte[]> findAvatar(Long id);

    String updateAvatar(Long id, MultipartFile image);

    boolean removeAvatar(Long id);
}