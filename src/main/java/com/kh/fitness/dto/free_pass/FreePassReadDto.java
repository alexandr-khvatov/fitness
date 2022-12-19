package com.kh.fitness.dto.free_pass;

import com.kh.fitness.dto.training.TrainingReadDto;
import com.kh.fitness.entity.Gym;
import com.kh.fitness.entity.Training;
import com.kh.fitness.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreePassReadDto {
    Long id;

    @NotBlank
    String firstname;

    String lastname;

    @Phone
    String phone;

    @Email
    String email;

    Boolean isDone;

    LocalDate date;
    LocalTime start;
    LocalTime end;
    Long  gymId;
    Long  trainingId;
    TrainingReadDto training;
}

