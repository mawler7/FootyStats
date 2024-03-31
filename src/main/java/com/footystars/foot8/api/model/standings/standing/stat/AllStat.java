package com.footystars.foot8.api.model.standings.standing.stat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.standings.standing.stat.goals.Goals;
import jakarta.persistence.Embeddable;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllStat implements Serializable {

    private Integer played;
    private Integer win;
    private Integer draw;
    private Integer lose;
    @Embedded
    private Goals goals;

}
