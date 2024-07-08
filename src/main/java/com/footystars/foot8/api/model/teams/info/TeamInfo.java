package com.footystars.foot8.api.model.teams.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamInfo implements Serializable {
    @JsonProperty("id")
    private Long clubId;
    private String name;
    private String code;
    private Integer founded;
    private boolean national;
    private String logo;
    private String country;
}
