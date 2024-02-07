package com.example.foot8.buisness.team;

import com.example.foot8.persistence.entities.fixtures.FixtureEntity;
import com.example.foot8.persistence.entities.teams.TeamEntityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;
@Value
@Builder
@Getter
@Setter
public class TeamDetailsDto {

    TeamEntityDto teamEntityDto;
    List<FixtureEntity> fixtures;
}