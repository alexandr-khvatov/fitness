package com.kh.fitness.dto.training;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCreateDto {
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private LocalTime start;

    @NotNull
    private LocalTime end;

    @NotNull
    @Min(1)
    @Max(7)
    private Integer dayOfWeek;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Integer takenSeats;

    @NotNull
    private Integer totalSeats;

    @NotNull
    private Long coachId;

    @NotNull
    private Long gymId;

    @NotNull
    private Long roomId;

    @NotNull
    private Long subProgramId;

}