package com.footystars.foot8.api.model.teams.statistics.model.goals.minutes;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GoalsWithin15MinStatistic implements Serializable {

    @JsonProperty("percentage")
    @Nullable
    private String percentage;

    @JsonProperty("total")
    @Nullable
    private Integer total;
}
