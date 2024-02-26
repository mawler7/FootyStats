package com.footystars.foot8.persistence.entities.teams;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamInfoDto {
    private Long teamId;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private boolean national;
    private String logo;
}