package com.kh.fitness.integration.service;

import com.kh.fitness.integration.IntegrationTestBase;
import com.kh.fitness.service.image.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RequiredArgsConstructor
class ImageServiceImplIT extends IntegrationTestBase {

    private final ImageServiceImpl imageService;

    private final Environment env;

    private Path path;

    @TempDir
    static Path sharedTempDir;

    @Test
    void checkProperty() {
        assertThat(env.getProperty("app.image.bucket")).isNotNull().isNotEmpty();
    }

    @Test
    void upload_shouldReturnFileName_whenSuccess() {
         path = sharedTempDir.resolve(env.getProperty("app.image.bucket"));
        var image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.TEXT_PLAIN_VALUE,
                "image".getBytes()
        );

        var actualResult = imageService.upload(image);

        assertThat(actualResult)
                .isNotNull()
                .isNotEmpty()
                .contains(".png");
    }

    @Test
    void get_shouldReturnEmptyOpt_whenFileNotExist() throws IOException {
         path = sharedTempDir.resolve(env.getProperty("app.image.bucket"));
        var imagePath = "test.img";

        var actualResult = imageService.get(imagePath);

        assertThat(actualResult).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getArguments")
    void upload_shouldReturnEmptyOpt_whenMultipartFileIsEmptyOrNull(InputStream inputStream) throws IOException {
        var path = sharedTempDir.resolve(env.getProperty("app.image.bucket"));
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, inputStream);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.upload(image);
        });

        String expectedMessage = "Image is empty";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).contains(expectedMessage);
    }

    public static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(InputStream.nullInputStream())
        );
    }
}