package com.footystars.foot8.api.model.teams.statistics.model.lineups;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Embeddable
public class Lineup implements Serializable {

    private String formation;
    private Long played;

}
