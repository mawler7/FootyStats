package com.footystars.foot8.persistence.entities.teams.statistics;

import com.footystars.foot8.api.model.teams.statistics.statistic.lineups.lineup.Lineup;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "team_stats")
public class TeamStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String form;

    //biggest
    private Integer biggestGoalsForHome;
    private Integer biggestGoalsForAway;
    private Integer biggestGoalsAgainstHome;
    private Integer biggestGoalsAgainstAway;
    private String biggestWinHome;
    private String biggestWinAway;
    private String biggestLostHome;
    private String biggestLostAway;
    private Integer biggestStreakDraws;
    private Integer biggestStreakLoses;
    private Integer biggestStreakWins;

    //cards
    private String redCardFrom0To15Percentage;
    private Integer redCardFrom0To15Total;
    private String redCardFrom16To30Percentage;
    private Integer redCardFrom16To30Total;
    private String redCardFrom31To45Percentage;
    private Integer redCardFrom31To45Total;
    private String redCardFrom46To60Percentage;
    private Integer redCardFrom46To60Total;
    private String redCardFrom61To75Percentage;
    private Integer redCardFrom61To75Total;
    private String redCardFrom76To90Percentage;
    private Integer redCardFrom76To90Total;
    private String redCardFrom91To105Percentage;
    private Integer redCardFrom91To105Total;
    private String redCardFrom106To120Percentage;
    private Integer redCardFrom106To120Total;
    private String yellowCardFrom0To15Percentage;
    private Integer yellowCardFrom0To15Total;
    private String yellowCardFrom16To30Percentage;
    private Integer yellowCardFrom16To30Total;
    private String yellowCardFrom31To45Percentage;
    private Integer yellowCardFrom31To45Total;
    private String yellowCardFrom46To60Percentage;
    private Integer yellowCardFrom46To60Total;
    private String yellowCardFrom61To75Percentage;
    private Integer yellowCardFrom61To75Total;
    private String yellowCardFrom76To90Percentage;
    private Integer yellowCardFrom76To90Total;
    private String yellowCardFrom91To105Percentage;
    private Integer yellowCardFrom91To105Total;
    private String yellowCardFrom106To120Percentage;
    private Integer yellowCardFrom106To120Total;

    //cleanSheets
    private Integer cleanSheetsHome;
    private Integer cleanSheetsAway;
    private Integer cleanSheetsTotal;
    //failed to score
    private Integer failedToScoreHome;
    private Integer failedToScoreAway;
    private Integer failedToScoreTotal;
    //goals
    private String goalsScoredAverageHome;
    private String goalsScoredAverageAway;
    private String goalsScoredAverageTotal;
    private String goalsScoredFrom0To15Percentage;
    private Integer goalsScoredFrom0To15Total;
    private String goalsScoredFrom16To30Percentage;
    private Integer goalsScoredFrom16To30Total;
    private String goalsScoredFrom31To45Percentage;
    private Integer goalsScoredFrom31To45Total;
    private String goalsScoredFrom46To60Percentage;
    private Integer goalsScoredFrom46To60Total;
    private String goalsScoredFrom61To75Percentage;
    private Integer goalsScoredFrom61To75Total;
    private String goalsScoredFrom76To90Percentage;
    private Integer goalsScoredFrom76To90Total;
    private String goalsScoredFrom91To105Percentage;
    private Integer goalsScoredFrom91To105Total;
    private String goalsScoredFrom106To120Percentage;
    private Integer goalsScoredFrom106To120Total;
    private Integer goalsScoredTotalHome;
    private Integer goalsScoredTotalAway;
    private String goalsConcededAverageHome;
    private String goalsConcededAverageAway;
    private String goalsConcededAverageTotal;
    private String goalsConcededFrom0To15Percentage;
    private Integer goalsConcededFrom0To15Total;
    private String goalsConcededFrom16To30Percentage;
    private Integer goalsConcededFrom16To30Total;
    private String goalsConcededFrom31To45Percentage;
    private Integer goalsConcededFrom31To45Total;
    private String goalsConcededFrom46To60Percentage;
    private Integer goalsConcededFrom46To60Total;
    private String goalsConcededFrom61To75Percentage;
    private Integer goalsConcededFrom61To75Total;
    private String goalsConcededFrom76To90Percentage;
    private Integer goalsConcededFrom76To90Total;
    private String goalsConcededFrom91To105Percentage;
    private Integer goalsConcededFrom91To105Total;
    private String goalsConcededFrom106To120Percentage;
    private Integer goalsConcededFrom106To120Total;
    private Integer goalsConcededTotalHome;
    private Integer goalsConcededTotalAway;
    //fixtures
    private Integer drawsHome;
    private Integer drawsAway;
    private Integer drawsTotal;
    private Integer losesHome;
    private Integer losesAway;
    private Integer losesTotal;
    private Integer playedTotal;
    private Integer playedHome;
    private Integer playedAway;
    private Integer winsTotal;
    private Integer winsHome;
    private Integer winsAway;
    //league
    private Long leagueId;
    private String leagueName;
    private String country;
    private String leagueLogo;
    private Integer seasonYear;
    //lineups
    @ElementCollection
    @CollectionTable(name = "team_lineups", joinColumns = @JoinColumn(name = "team_stats_id"))
    private List<Lineup> lineups;

    //penalty
    private Integer penaltiesTotal;
    private String penaltiesScoredTotal;
    private String penaltiesScoredPercentage;
    private Integer penaltiesMissedTotal;
    private String penaltiesMissedPercentage;


    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

}