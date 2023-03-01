package com.kh.fitness.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.fitness.dto.coach.CoachCreateDto;
import com.kh.fitness.dto.coach.CoachEditDto;
import com.kh.fitness.dto.coach.CoachReadDto;
import com.kh.fitness.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@RequiredArgsConstructor
class CoachControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    public static final String COACH_ID = Long.toString(1L);
    public static final String COACH_ID_NOT_EXIST = Long.toString(-128L);
    public static final String URL = API_V1 + "/coaches";
    public static final String URL_WITH_ID = URL + "/%s";

    public static final Long GYM_ID = 1L;
    public static final Long GYM_ID_NOT_EXIST = -128L;

    public static final String ERROR_MSG_NOT_FOUND = "Coach with id %s not found";

    @Test
    void findById_shouldReturnCoach_whenSucceed() throws Exception {
        this.mockMvc.perform(get(format(URL_WITH_ID, COACH_ID)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON),
                        jsonPath("$.id").value(COACH_ID)
                );
    }

    @Test
    void findById_shouldReturn404_whenCoachNotExist() throws Exception {
        this.mockMvc.perform(get(URL + "/" + COACH_ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Disabled("does not pass multipart and content")
    @Test
    void create_shouldReturnCreatedCoach_whenSucceed() throws Exception {
        var dto = getCoachCreateDto();
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
        String userJson = result.getResponse().getContentAsString();
        var actualDto = mapper.readValue(userJson, CoachReadDto.class);
        assertThat(actualDto.getId()).isNotNull();
        Assertions.assertAll(
                () -> assertThat(actualDto.getFirstname()).isEqualTo(dto.getFirstname()),
                () -> assertThat(actualDto.getPatronymic()).isEqualTo(dto.getPatronymic()),
                () -> assertThat(actualDto.getLastname()).isEqualTo(dto.getLastname()),
                () -> assertThat(actualDto.getBirthDate()).isEqualTo(dto.getBirthDate()),
                () -> assertThat(actualDto.getEmail()).isEqualTo(dto.getEmail()),
                () -> assertThat(actualDto.getPhone()).isEqualTo(dto.getPhone()),
                () -> assertThat(actualDto.getSpecialization()).isEqualTo(dto.getSpecialization()),
                () -> assertThat(actualDto.getDescription()).isEqualTo(dto.getDescription()),
                () -> assertThat(actualDto.getGymId()).isEqualTo(dto.getGymId())
        );
    }

    @Disabled("does not pass multipart and content")
    @Test
    void create_shouldReturnNotFound_whenCoachNotExist() throws Exception {
        var dto = getCoachCreateDtoWithGymIdNotExist();
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

    @ParameterizedTest(name = "#{index} - update user: with a different number of roles in the set {0} ")
    @MethodSource("getArgumentsRoles")
    void update_shouldReturnRoomUpdated_whenUpdatedSuccessfully(Set<Long> roles) throws Exception {
        var dto = getCoachEditDto();
        dto.setFirstname("update_firstname" + roles.size());
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(put(URL + "/" + COACH_ID)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(COACH_ID),
                        jsonPath("$.firstname", equalTo(dto.getFirstname()))
                );
    }

    static Stream<Arguments> getArgumentsRoles() {
        return Stream.of(
                Arguments.of(Set.of(1L)),
                Arguments.of(Set.of(1L, 2L)),
                Arguments.of(Set.of(1L, 2L, 3L))
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

    @DisplayName("deleteAvatar(id) -> should return status code 404 when user with id not exist")
    @ParameterizedTest
    @ValueSource(longs = {-128, -1})
    void deleteAvatar_shouldReturn404_whenCoachWithIdNotFound(Long deleteAvatarForCoachWithId) throws Exception {
        this.mockMvc.perform(delete(URL + "/" + deleteAvatarForCoachWithId + "/avatar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(format(ERROR_MSG_NOT_FOUND, deleteAvatarForCoachWithId))
                );
    }

    @DisplayName("deleteAvatar(id) -> should return status code 404 when Avatar not exist in file storage) ")
    @ParameterizedTest
    @ValueSource(longs = {1})
    void deleteAvatar_shouldReturn404_whenUserAvatarInFileStorageIsNullOrEmpty(Long deleteAvatarForCoachWithId) throws Exception {
        var result = this.mockMvc.perform(delete(URL + "/" + deleteAvatarForCoachWithId + "/avatar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound())
                .andReturn();
        var actualResult = result.getResponse().getErrorMessage();
        assertThat(actualResult).isEqualTo("File not exist");
    }

    @DisplayName("updateAvatar(id) -> should return status code 404 when user with id not exist) ")
    @ParameterizedTest
    @ValueSource(longs = {-128, -1})
    void updateAvatar_shouldReturn404_whenCoachWithIdNotFound(Long deleteAvatarForCoachWithId) throws Exception {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, "inputStream".getBytes());
        this.mockMvc.perform(multipart(PUT, URL + "/" + deleteAvatarForCoachWithId + "/avatar")
                        .file(image)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(format(ERROR_MSG_NOT_FOUND, deleteAvatarForCoachWithId))
                );
    }

    @DisplayName("updateAvatar(id) -> should return status code 404 when user.image is null or empty) ")
    @ParameterizedTest
    @ValueSource(longs = {1})
    void updateAvatar_shouldReturn404_whenEmptyFile(Long deleteAvatarForCoachWithId) throws Exception {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, InputStream.nullInputStream());
        this.mockMvc.perform(multipart(PUT, URL + "/" + deleteAvatarForCoachWithId + "/avatar")
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

    private CoachCreateDto getCoachCreateDto() {
        return CoachCreateDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .phone("88005553535")
                .email("test@yandex.ru")
                .specialization("coach_specialization")
                .description("coach_specialization")
                .gymId(GYM_ID)
                .build();
    }

    private CoachCreateDto getCoachCreateDtoWithGymIdNotExist() {
        return CoachCreateDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .phone("88005553535")
                .email("test@yandex.ru")
                .specialization("coach_specialization")
                .description("coach_specialization")
                .gymId(GYM_ID_NOT_EXIST)
                .build();
    }

    private CoachEditDto getCoachEditDto() {
        return CoachEditDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .phone("88005553535")
                .email("test@yandex.ru")
                .specialization("coach_specialization")
                .description("coach_specialization")
                .gymId(GYM_ID)
                .build();
    }
}