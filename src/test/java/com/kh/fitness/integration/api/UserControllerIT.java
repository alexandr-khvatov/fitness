package com.kh.fitness.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.fitness.dto.user.UserCreateDto;
import com.kh.fitness.dto.user.UserEditDto;
import com.kh.fitness.dto.user.UserReadDto;
import com.kh.fitness.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
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

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    public static final String USER_ID = Long.toString(1L);
    public static final String USERNAME = "+70000000001";
    public static final String USERNAME_NOT_EXIST = "88005553535";
    public static final String USER_ID_NOT_EXIST = Long.toString(-128L);
    public static final String URL = API_V1 + "/users";
    public static final String URL_WITH_ID = URL + "/%s";

    public static final Integer SIZE_RETURN_COLLECTION = 3;
    public static final String ERROR_MSG_NOT_FOUND = "User with id %s not found";

    @Test
    void findById_shouldReturnUser_whenSucceed() throws Exception {
        this.mockMvc.perform(get(format(URL_WITH_ID, USER_ID)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON),
                        jsonPath("$.id").value(USER_ID)
                );
    }

    @Test
    void findById_shouldReturn404_whenNotExist() throws Exception {
        this.mockMvc.perform(get(URL + "/" + USER_ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByUsername_shouldReturnUser_whenSucceed() throws Exception {
        var username = USERNAME;

        this.mockMvc.perform(get(URL + "/username/" + username))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON),
                        jsonPath("$.username").value(username)
                );
    }

    @Test
    void findByUsername_shouldReturn404_whenNotExist() throws Exception {
        this.mockMvc.perform(get(URL + "/username/" + USERNAME_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByUsername_shouldStatusCode400_whenUsernameIsBad() throws Exception {
        this.mockMvc.perform(get(URL + "/username/" + USERNAME_NOT_EXIST + "IsBad"))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value("BAD_REQUEST"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.subErrors").isArray(),
                        jsonPath("$.subErrors[0].message").value(containsString("Invalid phone"))
                )
                .andReturn();
    }

    @Test
    void getAll_shouldReturnUsers_whenSucceed() throws Exception {
        this.mockMvc.perform(get(URL))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON),
                        jsonPath("$").isArray(),
                        jsonPath("$.length()").value(SIZE_RETURN_COLLECTION)
                );
    }

    @Test
    void create_shouldReturnCreatedUser_whenSucceed() throws Exception {
        var dto = getUserCreateDto();
        var dtoToJson = mapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(APPLICATION_JSON))
                .andReturn();

        assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        var actualDto = mapper.readValue(userJson, UserReadDto.class);
        assertThat(actualDto.getId()).isNotNull();
        Assertions.assertAll(
                () -> assertThat(actualDto.getFirstname()).isEqualTo(dto.getFirstname()),
                () -> assertThat(actualDto.getPatronymic()).isEqualTo(dto.getPatronymic()),
                () -> assertThat(actualDto.getLastname()).isEqualTo(dto.getLastname()),
                () -> assertThat(actualDto.getBirthDate()).isEqualTo(dto.getBirthDate()),
                () -> assertThat(actualDto.getEmail()).isEqualTo(dto.getEmail()),
                () -> assertThat(actualDto.getPhone()).isEqualTo(dto.getPhone()),
                () -> assertThat(actualDto.getRoles()).isEqualTo(dto.getRoles())
        );
    }

    @Test
    void create_shouldReturnNotFound_whenRolesNotExist() throws Exception {
        var dto = getUserCreateDtoWithRolesNotExist();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void create_shouldReturnNotFound_whenOneRoleNotExist() throws Exception {
        var dto = getUserCreateDtoWithOneRoleNotExist();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(APPLICATION_JSON))
                .andReturn();
    }

    @ParameterizedTest(name = "#{index} - update user: with a different number of roles in the set {0} ")
    @MethodSource("getArgumentsRoles")
    void update_shouldReturnRoomUpdated_whenUpdatedSuccessfully(Set<Long> roles) throws Exception {
        var dto = getUserEditDto();
        dto.setFirstname("update_firstname" + roles.size());
        dto.setRoles(roles);
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(put(URL + "/" + USER_ID)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(USER_ID),
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
    void deleteAvatar_shouldReturn404_whenUserWithIdNotFound(Long deleteAvatarForUserWithId) throws Exception {
        this.mockMvc.perform(delete(URL + "/" + deleteAvatarForUserWithId + "/avatar")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(format(ERROR_MSG_NOT_FOUND, deleteAvatarForUserWithId))
                );
    }

    @DisplayName("deleteAvatar(id) -> should return status code 404 when user.image is null or empty) ")
    @ParameterizedTest
    @ValueSource(longs = {1})
    void deleteAvatar_shouldReturn404_whenUserAvatarIsNullOrEmpty(Long deleteAvatarForUserWithId) throws Exception {
        var result = this.mockMvc.perform(delete(URL + "/" + deleteAvatarForUserWithId + "/avatar")
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
    void updateAvatar_shouldReturn404_whenUserWithIdNotFound(Long deleteAvatarForUserWithId) throws Exception {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, "inputStream".getBytes());
        this.mockMvc.perform(multipart(PUT, URL + "/" + deleteAvatarForUserWithId + "/avatar")
                        .file(image)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(format(ERROR_MSG_NOT_FOUND, deleteAvatarForUserWithId))
                );
    }

    @DisplayName("updateAvatar(id) -> should return status code 404 when user.image is null or empty) ")
    @ParameterizedTest
    @ValueSource(longs = {1})
    void updateAvatar_shouldReturn404_whenEmptyFile(Long deleteAvatarForUserWithId) throws Exception {
        var image = new MockMultipartFile("image", "image.png", MediaType.TEXT_PLAIN_VALUE, InputStream.nullInputStream());
        this.mockMvc.perform(multipart(PUT, URL + "/" + deleteAvatarForUserWithId + "/avatar")
                        .file(image)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value("Image is empty")
                );
    }

    private UserCreateDto getUserCreateDto() {
        return UserCreateDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .password("super6$LiOCS0")
                .phone("88005553535")
                .email("test@yandex.ru")
                .roles(Set.of(1L, 2L, 3L))
                .build();
    }

    private UserCreateDto getUserCreateDtoWithRolesNotExist() {
        return UserCreateDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .password("super6$LiOCS0")
                .phone("88005553535")
                .email("test@yandex.ru")
                .roles(Set.of(1L, 2L, 3L, 4L))
                .build();
    }

    private UserCreateDto getUserCreateDtoWithOneRoleNotExist() {
        return UserCreateDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .password("super6$LiOCS0")
                .phone("88005553535")
                .email("test@yandex.ru")
                .roles(Set.of(4L))
                .build();
    }

    private UserEditDto getUserEditDto() {
        return UserEditDto.builder()
                .firstname("firstname")
                .patronymic("patronymic")
                .lastname("lastname")
                .birthDate(LocalDate.now())
                .phone(USERNAME)
                .email("test@yandex.ru")
                .roles(Set.of(1L, 2L, 3L))
                .build();
    }
}