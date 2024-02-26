package com.footystars.foot8.api.model.teams.statistics.model.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Goals implements Serializable {

    @Embedded
    @JsonProperty("for")
    private TeamStatisticGoals _for;

    @Embedded
    @JsonProperty("against")
    private TeamStatisticGoals against;

}