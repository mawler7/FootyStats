package com.example.foot8.api.teams.statistics.model.goals;

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
@Builder
@AllArgsConstructor
public class Goals implements Serializable {

    @Embedded
    @JsonProperty("for")
    private TeamStatisticGoals _for ;

    @Embedded
    @JsonProperty("against")
    private TeamStatisticGoals against ;

}