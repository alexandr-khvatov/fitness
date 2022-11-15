package com.kh.fitness.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class ImageService {
    private final String bucket;

    public ImageService(@Value("${app.image.bucket:C:\\Users\\khvatov-al\\IdeaProjects\\fitness\\images}") String bucket) {
        this.bucket = bucket;
    }

    public void upload(String imagePath, InputStream content) {
        Path fullPath = Path.of(bucket, imagePath);
        try {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<byte[]> getImage(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try {
            return Files.exists(fullImagePath)
                    ? Optional.of(Files.readAllBytes(fullImagePath))
                    : Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeImage(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try {
            return Files.deleteIfExists(fullImagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}