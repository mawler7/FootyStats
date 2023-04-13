package com.example.foot8.buisness.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreDto {
    private GoalsDto halftime;
    private GoalsDto fulltime;
    private GoalsDto extratime;
    private GoalsDto penalty;
}