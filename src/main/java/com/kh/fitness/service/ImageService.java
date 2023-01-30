package com.kh.fitness.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

public interface ImageService {
    void upload(String imagePath, InputStream content);

    Optional<String> uploadImage(MultipartFile image);

    Optional<byte[]> getImage(String imagePath);

    boolean removeImage(String imagePath);
}
