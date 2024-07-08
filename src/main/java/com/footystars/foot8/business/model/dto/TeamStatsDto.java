package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.teams.statistics.statistic.lineups.lineup.Lineup;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamStatsDto implements Serializable {

    private String clubName;
    private String clubLogo;
    private String leagueName;
    private String country;
    private String flag;
    private String leagueLogo;
    private String penaltiesScoredPercentage;
    private String penaltiesMissedPercentage;
    private String form;
    private String biggestWinHome;
    private String biggestWinAway;
    private String biggestLostHome;
    private String biggestLostAway;
    private String redCardFrom0To15Percentage;
    private String redCardFrom16To30Percentage;
    private String redCardFrom31To45Percentage;
    private String redCardFrom46To60Percentage;
    private String redCardFrom61To75Percentage;
    private String redCardFrom76To90Percentage;
    private String redCardFrom91To105Percentage;
    private String redCardFrom106To120Percentage;
    private String yellowCardFrom0To15Percentage;
    private String yellowCardFrom16To30Percentage;
    private String yellowCardFrom31To45Percentage;
    private String yellowCardFrom46To60Percentage;
    private String yellowCardFrom61To75Percentage;
    private String yellowCardFrom76To90Percentage;
    private String yellowCardFrom91To105Percentage;
    private String yellowCardFrom106To120Percentage;
    private String goalsScoredAverageHome;
    private String goalsScoredAverageAway;
    private String goalsScoredFrom0To15Percentage;
    private String goalsScoredFrom16To30Percentage;
    private String goalsScoredFrom31To45Percentage;
    private String goalsScoredFrom46To60Percentage;
    private String goalsScoredFrom61To75Percentage;
    private String goalsScoredFrom76To90Percentage;
    private String goalsScoredFrom91To105Percentage;
    private String goalsScoredFrom106To120Percentage;
    private String goalsConcededAverageHome;
    private String goalsConcededAverageAway;
    private String goalsConcededFrom0To15Percentage;
    private String goalsConcededFrom16To30Percentage;
    private String goalsConcededFrom31To45Percentage;
    private String goalsConcededFrom46To60Percentage;
    private String goalsConcededFrom61To75Percentage;
    private String goalsConcededFrom76To90Percentage;
    private String goalsConcededFrom91To105Percentage;
    private String goalsConcededFrom106To120Percentage;

    private Integer biggestStreakDraws;
    private Integer biggestStreakLoses;
    private Integer biggestStreakWins;
    private Integer biggestGoalsForHome;
    private Integer biggestGoalsForAway;
    private Integer biggestGoalsAgainstHome;
    private Integer biggestGoalsAgainstAway;
    private Integer redCardFrom0To15Total;
    private Integer redCardFrom16To30Total;
    private Integer redCardFrom31To45Total;
    private Integer redCardFrom46To60Total;
    private Integer redCardFrom61To75Total;
    private Integer redCardFrom76To90Total;
    private Integer redCardFrom91To105Total;
    private Integer redCardFrom106To120Total;
    private Integer yellowCardFrom0To15Total;
    private Integer yellowCardFrom16To30Total;
    private Integer yellowCardFrom31To45Total;
    private Integer yellowCardFrom46To60Total;
    private Integer yellowCardFrom61To75Total;
    private Integer yellowCardFrom76To90Total;
    private Integer yellowCardFrom91To105Total;
    private Integer yellowCardFrom106To120Total;
    private Integer cleanSheetsTotal;
    private Integer failedToScoreTotal;
    private String goalsScoredAverageTotal;
    private Integer goalsScoredFrom0To15Total;
    private Integer goalsScoredFrom16To30Total;
    private Integer goalsScoredFrom31To45Total;
    private Integer goalsScoredFrom46To60Total;
    private Integer goalsScoredFrom61To75Total;
    private Integer goalsScoredFrom76To90Total;
    private Integer goalsScoredFrom91To105Total;
    private Integer goalsScoredFrom106To120Total;
    private Integer goalsScoredTotalHome;
    private Integer goalsScoredTotalAway;
    private String goalsConcededAverageTotal;
    private Integer goalsConcededFrom0To15Total;
    private Integer goalsConcededFrom16To30Total;
    private Integer goalsConcededFrom31To45Total;
    private Integer goalsConcededFrom46To60Total;
    private Integer goalsConcededFrom61To75Total;
    private Integer goalsConcededFrom76To90Total;
    private Integer goalsConcededFrom91To105Total;
    private Integer goalsConcededFrom106To120Total;
    private Integer goalsConcededTotalHome;
    private Integer goalsConcededTotalAway;
    private Integer drawsTotal;
    private Integer losesTotal;
    private Integer playedTotal;
    private Integer winsTotal;
    private Integer penaltiesTotal;
    private String penaltiesScoredTotal;
    private Integer penaltiesMissedTotal;
    private Integer drawsHome;
    private Integer drawsAway;
    private Integer losesHome;
    private Integer losesAway;
    private Integer playedHome;
    private Integer playedAway;
    private Integer winsHome;
    private Integer winsAway;
    private Integer seasonYear;
    private Integer cleanSheetsHome;
    private Integer cleanSheetsAway;
    private Integer failedToScoreHome;
    private Integer failedToScoreAway;

    private Long leagueId;
    private Long clubId;


    private List<Lineup> lineups;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

}
