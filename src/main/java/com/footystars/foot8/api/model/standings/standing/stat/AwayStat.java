package com.footystars.foot8.api.model.standings.standing.stat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.standings.standing.stat.goals.Goals;
import jakarta.persistence.Embeddable;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class AwayStat implements Serializable {

    private Integer played;
    private Integer win;
    private Integer draw;
    private Integer lose;
    @JsonProperty("goals")
    private Goals awayGoals;


}
