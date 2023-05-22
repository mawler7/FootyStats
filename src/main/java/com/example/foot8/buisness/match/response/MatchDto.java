package com.example.foot8.buisness.match.response;

import com.example.foot8.buisness.match.model.FixtureDto;
import com.example.foot8.buisness.match.model.GoalsDto;
import com.example.foot8.buisness.match.model.LeagueDto;
import com.example.foot8.buisness.match.model.ScoreDto;
import com.example.foot8.buisness.match.model.TeamsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MatchDto {
    private FixtureDto fixture;
    private LeagueDto league;
    private TeamsDto teams;
    private GoalsDto goals;
    private ScoreDto score;
}