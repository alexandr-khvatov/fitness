package com.kh.fitness.dto.free_pass;

import com.kh.fitness.dto.training.TrainingReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreePassReadDto {
    Long id;
    String firstname;
    String lastname;
    String phone;
    String email;
    Boolean isDone;
    LocalDate date;
    LocalTime start;
    LocalTime end;
    Long gymId;
    Long trainingId;
    String trainingName;
    TrainingReadDto training;
}