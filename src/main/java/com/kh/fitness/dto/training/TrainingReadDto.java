package com.kh.fitness.dto.training;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingReadDto {
    private Long id;
    private String title;
    private LocalTime start;
    private LocalTime end;
    private Integer dayOfWeek;
    private LocalDate date;
    private Integer takenSeats;
    private Integer totalSeats;
    private Long coachId;
    private String coach;
    private Long gymId;
    private Long roomId;
    private String room;
    private Long subProgramId;
    private String subProgram;
    private Long programId;
}
