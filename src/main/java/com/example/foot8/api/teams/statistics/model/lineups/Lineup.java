package com.example.foot8.api.teams.statistics.model.lineups;

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
public class Lineup implements Serializable {

    private String formation;
    private Long played;

}
