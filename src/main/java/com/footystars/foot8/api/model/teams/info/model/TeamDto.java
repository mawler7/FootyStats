package com.footystars.foot8.api.model.teams.info.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamDto implements Serializable {
    @JsonProperty("id")
    private Long teamId;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private boolean national;
    private String logo;
}
