package com.kh.fitness.service.image;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ImageService {
    String upload(MultipartFile image);

    Optional<byte[]> get(String imagePath);

    boolean remove(@NotNull String imagePath);
}
