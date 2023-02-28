package com.kh.fitness.dto.training_program;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    private String overview;

    private String description;

    private Long gymId;

    MultipartFile image;
}
