package com.kh.fitness.service.image;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final String bucket;

    public ImageServiceImpl(@Value("${app.image.bucket:C:\\Users\\khvatov-al\\IdeaProjects\\fitness\\images}") String bucket) {
        this.bucket = bucket;
    }

    @SneakyThrows
    @Override
    public String upload(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            // only for .fileExtension
            final String IMAGE_NAME = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
            save(IMAGE_NAME, image.getInputStream());
            return IMAGE_NAME;
        } else {
            throw new IllegalArgumentException("Image is empty");
        }
    }

    private void save(String imagePath, InputStream content) {
        Path fullPath = Path.of(bucket, imagePath);
        try {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<byte[]> get(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try {
            return Files.exists(fullImagePath)
                    ? Optional.of(Files.readAllBytes(fullImagePath))
                    : Optional.empty();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean remove(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try {
            return Files.deleteIfExists(fullImagePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}