package com.footystars.foot8.api.model.teams.statistics.statistic.biggest.streak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Streak implements Serializable {

    private Integer draws;
    private Integer loses;
    private Integer wins;
}
