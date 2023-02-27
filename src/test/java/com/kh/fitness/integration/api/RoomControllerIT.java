package com.kh.fitness.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.dto.room.RoomEditDto;
import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static com.kh.fitness.api.util.PathUtils.API_V1;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@RequiredArgsConstructor
class RoomControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    public static final String ROOM_ID = Long.toString(1L);
    public static final Long ROOM_ID_FOR_DELETE = 3L;
    public static final String ROOM_ID_NOT_EXIST = Long.toString(-128L);
    public static final String URL = API_V1 + "/rooms";
    public static final String URL_BY_GYM_ID = API_V1 + "/gyms/%s/rooms";

    public static final Long GYM_ID = 1L;
    public static final Long GYM_ID_NOT_EXIST = -128L;

    public static final String ERROR_MSG_NOT_FOUND = "Room with id %s not found";
    public static final String ERROR_MSG_NOT_FOUND_BY_GYM_ID = "Rooms with gymId %s not found";

    @Test
    void findById_shouldReturnRoom_whenSucceed() throws Exception {
        this.mockMvc.perform(get(URL + "/" + ROOM_ID))
                .andExpect(status().isOk());
    }

    @Test
    void findById_shouldReturn404_whenNotExist() throws Exception {
        this.mockMvc.perform(get(URL + "/" + ROOM_ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllByGymId_shouldReturnRooms_whenSucceed() throws Exception {
        this.mockMvc.perform(get(format(URL_BY_GYM_ID, GYM_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void findAllByGymId_should404_whenNotExist() throws Exception {
        var result = this.mockMvc.perform(get(format(URL_BY_GYM_ID, GYM_ID_NOT_EXIST))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(result).isNotNull();
        String roomJson = result.getResponse().getErrorMessage();
        assertThat(roomJson).contains(format(ERROR_MSG_NOT_FOUND_BY_GYM_ID, GYM_ID_NOT_EXIST));
    }

    @Test
    void create_shouldReturnCreatedRoom_whenCreatedSuccessfully() throws Exception {
        var dto = getRoomCreateDto();
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
        var actualDto = mapper.readValue(roomJson, RoomReadDto.class);
        assertThat(actualDto.getId()).isNotNull();
        Assertions.assertAll(
                () -> assertThat(actualDto.getGymId()).isEqualTo(dto.getGymId()),
                () -> assertThat(actualDto.getName()).isEqualTo(dto.getName())
        );
    }

    @Test
    void create_shouldReturnNotFound_whenGymIdNotExistInDb() throws Exception {
        var dto = getRoomCreateDtoWithGymIdNotExist();
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
    void update_shouldReturnRoomUpdated_whenUpdatedSuccessfully() throws Exception {
        var dto = getRoomEditDto();
        var dtoToJson = mapper.writeValueAsString(dto);

        this.mockMvc.perform(put(URL + "/" + ROOM_ID)
                        .contentType(APPLICATION_JSON)
                        .content(dtoToJson.getBytes(StandardCharsets.UTF_8))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(ROOM_ID),
                        jsonPath("$.name").value(dto.getName()),
                        jsonPath("$.gymId").value(dto.getGymId()));
    }

    @DisplayName("delete(id) -> should return status 204(no content) when deleted successfully")
    @Test
    void delete_shouldReturnNoContent_whenDeletedSuccessfully() throws Exception {
        this.mockMvc.perform(delete(URL + "/" + ROOM_ID_FOR_DELETE)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @DisplayName("delete(id) -> should return status 404(Not Found) when not exist")
    @Test
    void delete_shouldReturn404_whenNotExist() throws Exception {
        var delete_room_id_not_exist=ROOM_ID_NOT_EXIST;

        var result = this.mockMvc.perform(delete(URL + "/" + delete_room_id_not_exist)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        var actualResult = result.getResponse().getErrorMessage();
        assertThat(actualResult).isEqualTo(format(ERROR_MSG_NOT_FOUND, delete_room_id_not_exist));
    }

    private RoomCreateDto getRoomCreateDto() {
        return RoomCreateDto.builder()
                .name("room_create")
                .gymId(GYM_ID)
                .build();
    }

    private RoomEditDto getRoomEditDto() {
        return RoomEditDto.builder()
                .name("room_create")
                .gymId(GYM_ID)
                .build();
    }

    private RoomCreateDto getRoomCreateDtoWithGymIdNotExist() {
        return RoomCreateDto.builder()
                .name("room_create_with_gymId_not_exist")
                .gymId(GYM_ID_NOT_EXIST)
                .build();
    }
}