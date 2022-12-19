package com.kh.fitness.dto.free_pass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreePassEditTrainingDto {

    Long trainingId;
    LocalDate date;
}
