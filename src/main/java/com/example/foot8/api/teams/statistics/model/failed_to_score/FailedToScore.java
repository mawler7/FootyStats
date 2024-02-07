package com.example.foot8.api.teams.statistics.model.failed_to_score;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
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
