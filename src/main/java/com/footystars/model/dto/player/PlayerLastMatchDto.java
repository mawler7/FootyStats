package com.footystars.model.dto.player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerLastMatchDto {
    private Long id;
    private ZonedDateTime matchDate;
    private String leagueLogo;
    private String leagueName;
    private String homeTeamName;
    private Integer homeScore;
    private String homeTeamLogo;
    private String awayTeamName;
    private Integer awayScore;
    private String awayTeamLogo;
    private int minutes;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private String rating;
}
