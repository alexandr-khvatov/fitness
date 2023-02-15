package com.kh.fitness.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {
    Optional<String> upload(MultipartFile image);

    Optional<byte[]> get(String imagePath);

    boolean remove(String imagePath);
}
