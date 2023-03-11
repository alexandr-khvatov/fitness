package com.kh.fitness.dto.free_pass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreePassEditDto {
    @NotNull
    Long gymId;

    @NotNull
    Long trainingId;

    @Future
    LocalDateTime date;
}
