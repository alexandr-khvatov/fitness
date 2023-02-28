package com.kh.fitness.integration.service;

import com.kh.fitness.integration.IntegrationTestBase;
import com.kh.fitness.integration.annotation.IT;
import com.kh.fitness.service.image.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IT
@RequiredArgsConstructor
class ImageServiceImplIT extends IntegrationTestBase {

    private final ImageServiceImpl imageService;
    private final Environment env;

    public static final String name = "imageName";
    public static final String imageExtension = ".png";
    public static final String imagePath = name + imageExtension;

    private static final MockMultipartFile imageMock = new MockMultipartFile(
            imagePath,
            imagePath,
            MediaType.TEXT_PLAIN_VALUE,
            imagePath.getBytes()
    );

    @Test
    void upload_shouldReturnFileName_whenSuccess() throws IOException {
        // check test resource
        String bucket = env.getProperty("app.image.bucket");
        checkExistProperty(bucket);

        // given when
        var actualResult = imageService.upload(imageMock);

        // then
        assertThat(actualResult)
                .isNotNull()
                .isNotEmpty()
                .contains(imageExtension);

        assertThat(Files.exists(Path.of(bucket, actualResult))).isTrue();

        //clear test resource
        cleanup(Path.of(bucket));
    }

    @Test
    void get_shouldReturnImage_whenFileExist() throws IOException {
        // check test resource
        String bucket = env.getProperty("app.image.bucket");
        checkExistProperty(bucket);

        // given
        save(bucket, imageMock.getName(), imageMock.getInputStream());

        // when
        var actualResult = imageService.get(imagePath);

        // then
        assertThat(actualResult).isPresent().contains(imageMock.getBytes());

        //clear test resource
        cleanup(Path.of(bucket));
    }

    @Test
    void get_shouldReturnEmptyOpt_whenFileNotExist() {
        var imagePath = "notExist";

        var actualResult = imageService.get(imagePath);

        assertThat(actualResult).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("getArgumentsInputStreams")
    void upload_shouldReturnEmptyOpt_whenMultipartFileIsEmptyOrNull(InputStream inputStream) throws IOException {
        // check test resource
        String bucket = env.getProperty("app.image.bucket");
        checkExistProperty(bucket);

        // given
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, inputStream);

        // when-then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.upload(image);
        });

        String expectedMessage = "Image is empty";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).contains(expectedMessage);
    }

    public static Stream<Arguments> getArgumentsInputStreams() {
        return Stream.of(
                Arguments.of(InputStream.nullInputStream())
        );
    }

    private static void checkExistProperty(String bucket) {
        if (Objects.isNull(bucket) || bucket.isEmpty()) {
            Assertions.fail("Property is null or empty");
        }
    }

    private static void save(String bucket, String imagePath, InputStream content) {
        Path fullPath = Path.of(bucket, imagePath);
        try {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    void cleanup(Path file) throws IOException {
        if (Files.exists(file)) {
            FileSystemUtils.deleteRecursively(file);
        }
    }
}