package com.example.foot8.api.teams.statistics.model.biggest.goals;

import com.example.foot8.api.teams.statistics.model.goals.total.GoalsTotal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
    private GoalsTotal  _for;
}