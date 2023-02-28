package com.kh.fitness.entity.gym;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public
class GymSocialMedia {
    private String vkLink;
    private String tgLink;
    private String instLink;
}
