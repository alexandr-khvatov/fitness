package com.kh.fitness.dto.training;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEditDto {
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull
    private LocalTime start;
    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull
    private LocalTime end;
    private Integer dayOfWeek;
    private LocalDateTime date;
    private Integer takenSeats;
    private Integer totalSeats;
    private Long coachId;
    private Long gymId;
    private Long roomId;
    private Long subProgramId;
}
