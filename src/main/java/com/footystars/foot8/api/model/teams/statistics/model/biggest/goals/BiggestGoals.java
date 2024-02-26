package com.footystars.foot8.api.model.teams.statistics.model.biggest.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.model.goals.total.GoalsTotal;
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
@AllArgsConstructor
@Builder
public class BiggestGoals implements Serializable {

    @Embedded
    @JsonProperty("against")
    private GoalsTotal against;

    @Embedded
    @JsonProperty("for")
    private GoalsTotal _for;
}