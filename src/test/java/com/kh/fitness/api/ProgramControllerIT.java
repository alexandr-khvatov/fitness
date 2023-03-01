package com.kh.fitness.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.fitness.dto.training_program.ProgramCreateDto;
import com.kh.fitness.dto.training_program.ProgramEditDto;
import com.kh.fitness.dto.training_program.ProgramReadDto;
import com.kh.fitness.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class ProgramControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    public static final String PROGRAM_ID = Long.toString(1L);
    public static final String PROGRAM_ID_NOT_EXIST = Long.toString(-128L);
    public static final String URL = API_V1 + "/programs";
    public static final String URL_WITH_ID = URL + "/%s";

    public static final Long GYM_ID = 1L;
    public static final Long GYM_ID_NOT_EXIST = -128L;

    public static final String ERROR_MSG_NOT_FOUND = "Program with id %s not found";

    @Test
    void findById_shouldReturnProgram_whenSucceed() throws Exception {
        this.mockMvc.perform(get(format(URL_WITH_ID, PROGRAM_ID)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON),
                        jsonPath("$.id").value(PROGRAM_ID)
                );
    }

    @Test
    void findById_shouldReturn404_whenProgramNotExist() throws Exception {
        this.mockMvc.perform(get(URL + "/" + PROGRAM_ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Disabled("does not pass multipart and content")
    @Test
    void create_shouldReturnCreatedProgram_whenSucceed() throws Exception {
        var dto = getProgramCreateDto();
        var dtoToJson = mapper.writeValueAsBytes(dto);

        var result = this.mockMvc.perform(multipart(URL).file(imageMock)
                        .contentType(MULTIPART_FORM_DATA_VALUE)
                        .content(dtoToJson)
                        .accept(MULTIPART_FORM_DATA_VALUE))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MULTIPART_FORM_DATA_VALUE))
                .andReturn();

        assertThat(result).isNotNull();
        String programJson = result.getResponse().getContentAsString();
        var actualDto = mapper.readValue(programJson, ProgramReadDto.class);
        assertThat(actualDto.getId()).isNotNull();
        Assertions.assertAll(
                () -> assertThat(actualDto.getName()).isEqualTo(dto.getName()),
                () -> assertThat(actualDto.getOverview()).isEqualTo(dto.getOverview()),
                () -> assertThat(actualDto.getDescription()).isEqualTo(dto.getDescription()),
                () -> assertThat(actualDto.getGymId()).isEqualTo(dto.getGymId())
        );
    }

    @Disabled("does not pass multipart and content")
    @Test
    void create_shouldReturnNotFound_whenProgramNotExist() throws Exception {
        var dto = getProgramCreateDtoWithGymIdNotExist();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(multipart(URL).file(imageMock)
                        .contentType(MULTIPART_FORM_DATA_VALUE)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(MULTIPART_FORM_DATA_VALUE))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MULTIPART_FORM_DATA_VALUE))
                .andReturn();
    }

    @Test
    void update_shouldReturnProgramUpdated_whenUpdatedSuccessfully() throws Exception {
        var dto = getProgramEditDto();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(put(URL + "/" + PROGRAM_ID)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(PROGRAM_ID),
                        jsonPath("$.name", equalTo(dto.getName()))
                );
    }

    @DisplayName("delete(id) -> should return status 204(no content) when deleted successfully")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void delete_shouldReturnNoContent_whenDeletedSuccessfully(Long deleteId) throws Exception {
        this.mockMvc.perform(delete(URL + "/" + deleteId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @DisplayName("delete(id) -> should return status 404(Not Found) when not exist")
    @ParameterizedTest
    @ValueSource(longs = {-128, -1})
    void delete_shouldReturn404_whenNotExist(Long deleteId) throws Exception {
        var result = this.mockMvc.perform(delete(URL + "/" + deleteId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        var actualResult = result.getResponse().getErrorMessage();
        assertThat(actualResult).isEqualTo(format(ERROR_MSG_NOT_FOUND, deleteId));
    }

    @DisplayName("deleteAvatar(id) -> should return status code 404 when program with id not exist")
    @ParameterizedTest
    @ValueSource(longs = {-128, -1})
    void deleteAvatar_shouldReturn404_whenProgramWithIdNotFound(Long deleteAvatarForProgramWithId) throws Exception {
        this.mockMvc.perform(delete(URL + "/" + deleteAvatarForProgramWithId + "/avatar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(format(ERROR_MSG_NOT_FOUND, deleteAvatarForProgramWithId))
                );
    }

    @DisplayName("deleteAvatar(id) -> should return status code 404 when Avatar not exist in file storage) ")
    @ParameterizedTest
    @ValueSource(longs = {1})
    void deleteAvatar_shouldReturn404_whenProgramAvatarInFileStorageIsNullOrEmpty(Long deleteAvatarForProgramWithId) throws Exception {
        var result = this.mockMvc.perform(delete(URL + "/" + deleteAvatarForProgramWithId + "/avatar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound())
                .andReturn();
        var actualResult = result.getResponse().getErrorMessage();
        assertThat(actualResult).isEqualTo("File not exist");
    }

    @DisplayName("updateAvatar(id) -> should return status code 404 when program with id not exist) ")
    @ParameterizedTest
    @ValueSource(longs = {-128, -1})
    void updateAvatar_shouldReturn404_whenProgramWithIdNotFound(Long deleteAvatarForProgramWithId) throws Exception {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, "inputStream".getBytes());
        this.mockMvc.perform(multipart(PUT, URL + "/" + deleteAvatarForProgramWithId + "/avatar")
                        .file(image)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(format(ERROR_MSG_NOT_FOUND, deleteAvatarForProgramWithId))
                );
    }

    @DisplayName("updateAvatar(id) -> should return status code 404 when program.image is null or empty) ")
    @ParameterizedTest
    @ValueSource(longs = {1})
    void updateAvatar_shouldReturn404_whenEmptyFile(Long deleteAvatarForProgramWithId) throws Exception {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, InputStream.nullInputStream());
        this.mockMvc.perform(multipart(PUT, URL + "/" + deleteAvatarForProgramWithId + "/avatar")
                        .file(image)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value("Image is empty")
                );
    }

    public static final String name = "imageName";
    public static final String imageExtension = ".png";
    public static final String imagePath = name + imageExtension;

    private static final MockMultipartFile imageMock;

    static {
        try {
            imageMock = new MockMultipartFile(
                    imagePath,
                    imagePath,
                    MediaType.TEXT_PLAIN_VALUE,
                    InputStream.nullInputStream()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ProgramCreateDto getProgramCreateDto() {
        return ProgramCreateDto.builder()
                .name("program_name")
                .overview("program_overview")
                .description("program_description")
                .gymId(GYM_ID)
                .build();
    }

    private ProgramCreateDto getProgramCreateDtoWithGymIdNotExist() {
        return ProgramCreateDto.builder()
                .name("program_name")
                .overview("program_overview")
                .description("program_description")
                .gymId(GYM_ID_NOT_EXIST)
                .build();
    }

    private ProgramEditDto getProgramEditDto() {
        return ProgramEditDto.builder()
                .name("program_name")
                .overview("program_overview")
                .description("program_description")
                .gymId(GYM_ID_NOT_EXIST)
                .build();
    }
}