package com.footystars.model.dto.team;

import com.footystars.model.dto.fixture.ClubMatchDto;
import com.footystars.model.dto.fixture.TeamPredictionStats;
import com.footystars.model.dto.league.LeagueInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClubDto {
    private Long clubId;
    private String teamName;
    private String logo;
    private String venueName;
    private String image;
    private Integer capacity;
    private String name;
    private List<LeagueInfoDto> leagues= new ArrayList<>();
    private List<ClubMatchDto> fixtures = new ArrayList<>();
    private TeamPredictionStats predictionStats;

    public ClubDto(Long clubId, String teamName, String logo, String venueName, String image, Integer capacity, String name) {
        this.clubId = clubId;
        this.teamName = teamName;
        this.logo = logo;
        this.venueName = venueName;
        this.image = image;
        this.capacity = capacity;
        this.name = name;
        this.predictionStats = null;
    }
}
