package com.kh.fitness.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    private final String DIR = "test";

    private final ImageServiceImpl imageService = new ImageServiceImpl(DIR);

    @TempDir
    Path tempDir;

    @Test
    void upload_shouldReturnFileName_whenSuccess() {
        var image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.TEXT_PLAIN_VALUE,
                "image".getBytes()
        );

        var actualResult = imageService.upload(image);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).contains(".png");
    }

    @Test
    void get_shouldReturnEmptyOpt_whenFileNotExist() throws IOException {
        var imagePath = "test.img";

        var actualResult = imageService.get(imagePath);

        assertThat(actualResult).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getArguments")
    void upload_shouldReturnEmptyOpt_whenMultipartFileIsEmptyOrNull(InputStream inputStream) throws IOException {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, inputStream);

        var actualResult = imageService.upload(image);

        assertThat(actualResult).isEmpty();
    }

    public static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(InputStream.nullInputStream())
        );
    }
}