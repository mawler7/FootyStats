package com.footystars.model.dto.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
public class ClubMatchDto implements Serializable {
    private Long id;
    private ZonedDateTime date;
    private String shortStatus;
    private Long leagueId;
    private String logo;
    private String leagueName;
    private Integer season;
    private Long homeId;
    private String homeName;
    private String homeLogo;
    private Integer fullTimeHome;
    private Integer fullTimeAway;
    private Long awayId;
    private String awayName;
    private String awayLogo;



}
