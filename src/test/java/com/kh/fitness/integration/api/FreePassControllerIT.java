package com.kh.fitness.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.fitness.dto.free_pass.FreePassCreateDto;
import com.kh.fitness.dto.free_pass.FreePassEditDto;
import com.kh.fitness.dto.free_pass.FreePassReadDto;
import com.kh.fitness.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class FreePassControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    public static final String FREE_PASS_ID = Long.toString(1L);
    public static final String FREE_PASS_ID_NOT_EXIST = Long.toString(-128L);
    public static final String URL = API_V1 + "/free-pass";
    public static final String URL_WITH_ID = URL + "/%s";

    public static final Long GYM_ID = 1L;
    public static final Long GYM_ID_NOT_EXIST = -128L;
    public static final Long TRAINING_ID = 1L;
    public static final Long TRAINING_ID_NOT_EXIST = -128L;

    public static final String ERROR_MSG_NOT_FOUND = "Free pass with id %s not found";

    @Test
    void findById_shouldReturnFreePass_whenSucceed() throws Exception {
        this.mockMvc.perform(get(format(URL_WITH_ID, FREE_PASS_ID)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON),
                        jsonPath("$.id").value(FREE_PASS_ID)
                );
    }

    @Test
    void findById_shouldReturn404_whenFreePassNotExist() throws Exception {
        this.mockMvc.perform(get(URL + "/" + FREE_PASS_ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Disabled("Email not configured")
    @Test
    void create_shouldReturnCreatedRoom_whenCreatedSuccessfully() throws Exception {
        var dto = getFreePassCreateDto();
        var dtoToJson = mapper.writeValueAsString(dto);
        var result = this.mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        assertThat(result).isNotNull();
        String roomJson = result.getResponse().getContentAsString();
        var actualDto = mapper.readValue(roomJson, FreePassReadDto.class);
        assertThat(actualDto.getId()).isNotNull();
        Assertions.assertAll(
                () -> assertThat(actualDto.getGymId()).isEqualTo(dto.getGymId()),
                () -> assertThat(actualDto.getTrainingId()).isEqualTo(dto.getTrainingId()),
                () -> assertThat(actualDto.getStart()).isEqualTo(dto.getStart()),
                () -> assertThat(actualDto.getEnd()).isEqualTo(dto.getEnd()),
                () -> assertThat(actualDto.getEmail()).isEqualTo(dto.getEmail()),
                () -> assertThat(actualDto.getFirstname()).isEqualTo(dto.getFirstName())
        );
    }

    @Test
    void create_shouldReturnNotFound_whenGymIdNotExistInDb() throws Exception {
        var dto = getFreePassCreateDtoWithGymIdNotExist();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void update_shouldReturnFreePassUpdated_whenUpdatedSuccessfully() throws Exception {
        var dto = getFreePassEditDto();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(put(URL + "/" + FREE_PASS_ID)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(FREE_PASS_ID),
                        jsonPath("$.trainingId").value(dto.getTrainingId()),
                        jsonPath("$.gymId").value(dto.getGymId())
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

    private FreePassCreateDto getFreePassCreateDto() {
        return FreePassCreateDto.builder()
                .firstName("freepass_name")
                .lastName("freepass_overview")
                .phone("88005553535")
                .email("freepass_test@test.ru")
                .gymId(GYM_ID)
                .trainingId(TRAINING_ID)
                .date(LocalDate.now())
                .start(LocalTime.of(9,0))
                .end(LocalTime.of(10,0))
                .build();
    }

    private FreePassCreateDto getFreePassCreateDtoWithGymIdNotExist() {
        return FreePassCreateDto.builder()
                .firstName("freepass_firstname")
                .lastName("freepass_lastname")
                .phone("88005553535")
                .email("freepass_test@test.ru")
                .gymId(GYM_ID_NOT_EXIST)
                .trainingId(TRAINING_ID_NOT_EXIST)
                .date(LocalDate.now())
                .start(LocalTime.of(9,0))
                .end(LocalTime.of(10,0))
                .build();
    }

    private FreePassEditDto getFreePassEditDto() {
        return FreePassEditDto.builder()
                .gymId(GYM_ID)
                .trainingId(TRAINING_ID)
                .date(LocalDateTime.now())
                .build();
    }

}