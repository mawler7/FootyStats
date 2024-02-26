package com.footystars.foot8.api.model.teams.statistics.model.failed2score;

import jakarta.persistence.Embeddable;
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
public class FailedToScore implements Serializable {

    private int home;
    private int away;
    private int total;


}
