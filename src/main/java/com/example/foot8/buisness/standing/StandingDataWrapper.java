package com.example.foot8.buisness.standing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandingDataWrapper {
    private String leagueName;
    private Integer season;
    private List<StandingData> standingData;
}
