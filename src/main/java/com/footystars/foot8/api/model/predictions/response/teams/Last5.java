package com.footystars.foot8.api.model.predictions.response.teams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.predictions.response.teams.league.GoalsStats;
import jakarta.persistence.Embedded;
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
public class Last5 implements Serializable {

    private String form;
    private String att;
    private String def;
    @Embedded
    private GoalsStats goals;

}
