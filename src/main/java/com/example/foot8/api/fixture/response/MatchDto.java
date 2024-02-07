package com.example.foot8.api.fixture.response;

import com.example.foot8.api.fixture.model.FixtureDto;
import com.example.foot8.api.fixture.model.GoalsDto;
import com.example.foot8.api.fixture.model.LeagueDto;
import com.example.foot8.api.fixture.model.ScoreDto;
import com.example.foot8.api.fixture.model.TeamsDto;
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