package com.kh.fitness.dto.subtraining_program;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubProgramCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    private String overview;

    private String description;

    private Long programId;

    MultipartFile image;
}
