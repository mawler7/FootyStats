package com.footystars.foot8.api.model.predictions.response.teams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class TeamApi implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String logo;
    private Integer founded;
    private boolean national;
}
