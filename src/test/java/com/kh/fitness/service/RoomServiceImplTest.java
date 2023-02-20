package com.kh.fitness.service;

import com.kh.fitness.dto.room.RoomCreateDto;
import com.kh.fitness.dto.room.RoomEditDto;
import com.kh.fitness.dto.room.RoomReadDto;
import com.kh.fitness.entity.Room;
import com.kh.fitness.mapper.room.RoomCreateMapper;
import com.kh.fitness.mapper.room.RoomEditMapper;
import com.kh.fitness.mapper.room.RoomReadMapper;
import com.kh.fitness.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomReadMapper roomReadMapper;
    @Mock
    private RoomCreateMapper roomCreateMapper;
    @Mock
    private RoomEditMapper roomEditMapper;
    @InjectMocks
    private RoomServiceImpl roomService;

    private static final Long ROOM_ID = 1L;

    @Test
    void findById_shouldReturnRoom_whenExist() {
        var room = getRoomWithId();
        RoomReadDto roomDto = getRoomReadDto();

        doReturn(roomDto).when(roomReadMapper).toDto(room);
        doReturn(Optional.of(room)).when(roomRepository).findById(ROOM_ID);
        Optional<RoomReadDto> actualResult = roomService.findById(ROOM_ID);

        assertThat(actualResult).isPresent().isEqualTo(Optional.of(roomDto));
        verify(roomRepository).findById(any());
        verify(roomReadMapper).toDto(any());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenMissing() {
        doReturn(Optional.empty()).when(roomRepository).findById(anyLong());
        Optional<RoomReadDto> actualResult = roomService.findById(anyLong());

        assertThat(actualResult).isEmpty();
        verify(roomRepository, times(1)).findById(any());
        verify(roomReadMapper, times(0)).toDto(any());
    }


    @Test
    void create() {
        var room = getRoomWithoutId();
        var roomCreateDto = getRoomCreateDto();
        var savedRoom = getRoomWithId();
        var roomDto = getRoomReadDto();

        doReturn(room).when(roomCreateMapper).toEntity(roomCreateDto);
        doReturn(savedRoom).when(roomRepository).saveAndFlush(room);
        doReturn(roomDto).when(roomReadMapper).toDto(savedRoom);
        RoomReadDto actualResult = roomService.create(roomCreateDto);

        assertThat(actualResult).isNotNull().isEqualTo(roomDto);
        verify(roomCreateMapper).toEntity(any());
        verify(roomRepository).saveAndFlush(any());
        verify(roomReadMapper).toDto(any());
    }

    @Test
    void update() {
        var room = getRoomWithId();
        var roomEditDto = getRoomEditDto();
        var roomDto = getRoomReadDto();

        doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());
        doReturn(room).when(roomEditMapper).updateEntity(roomEditDto, room);
        doReturn(room).when(roomRepository).saveAndFlush(room);
        doReturn(roomDto).when(roomReadMapper).toDto(room);
        var actualResult = roomService.update(anyLong(), roomEditDto);

        assertThat(actualResult).isPresent().contains(roomDto);
        verify(roomEditMapper).updateEntity(any(), any());
        verify(roomRepository).saveAndFlush(any());
        verify(roomReadMapper).toDto(any());
    }

    @Test
    void delete_shouldReturnTrue_whenRoomExistAndDeletedSuccessfully() {
        var room = getRoomWithId();

        doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());
        doNothing().when(roomRepository).delete(room);
        var actualResult = roomService.delete(anyLong());

        assertThat(actualResult).isTrue();
        verify(roomRepository).findById(any());
        verify(roomRepository).delete(any());
        verify(roomRepository).flush();
    }

    @Test
    void delete_shouldReturnFalse_whenRoomMissing() {
        doReturn(Optional.empty()).when(roomRepository).findById(anyLong());
        var actualResult = roomService.delete(anyLong());

        assertThat(actualResult).isFalse();
        verify(roomRepository).findById(any());
        verify(roomRepository,times(0)).delete(any());
        verify(roomRepository,times(0)).flush();
    }

    private Room getRoomWithoutId() {
        return Room.builder()
                .id(null)
                .name("Room without id")
                .build();
    }

    private Room getRoomWithId() {
        return Room.builder()
                .id(ROOM_ID)
                .name("Room №1 with id")
                .build();
    }

    private RoomReadDto getRoomReadDto() {
        return RoomReadDto.builder()
                .id(ROOM_ID)
                .name("RoomReadDto")
                .build();
    }

    private RoomCreateDto getRoomCreateDto() {
        return RoomCreateDto.builder()
                .name("RoomCreateDto")
                .gymId(1L)
                .build();
    }

    private RoomEditDto getRoomEditDto() {
        return RoomEditDto.builder()
                .name("RoomEditDto")
                .gymId(1L)
                .build();
    }
}