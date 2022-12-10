package com.kh.fitness.dto.training;

import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime date;
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
