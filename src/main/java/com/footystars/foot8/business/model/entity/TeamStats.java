package com.footystars.foot8.business.model.entity;

import com.footystars.foot8.api.model.teams.statistics.statistic.lineups.lineup.Lineup;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "teams_statistics")
public class TeamStats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String  goalsScoredAverageTotal;
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
    private String  goalsConcededAverageTotal;
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


    @Column(name = "last_updated", columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime lastUpdated;

    @ElementCollection
    @CollectionTable(name = "teams_lineups", joinColumns = @JoinColumn(name = "team_id"))
    private List<Lineup> lineups;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = ZonedDateTime.now();
    }


    public void setDefaults() {
        if (this.clubName == null) this.clubName = "-";
        if (this.clubLogo == null) this.clubLogo = "-";
        if (this.leagueName == null) this.leagueName = "-";
        if (this.country == null) this.country = "-";
        if (this.flag == null) this.flag = "-";
        if (this.leagueLogo == null) this.leagueLogo = "-";
        if (this.penaltiesScoredPercentage == null) this.penaltiesScoredPercentage = "-";
        if (this.penaltiesMissedPercentage == null) this.penaltiesMissedPercentage = "-";
        if (this.form == null) this.form = "-";
        if (this.biggestWinHome == null) this.biggestWinHome = "-";
        if (this.biggestWinAway == null) this.biggestWinAway = "-";
        if (this.biggestLostHome == null) this.biggestLostHome = "-";
        if (this.biggestLostAway == null) this.biggestLostAway = "-";
        if (this.redCardFrom0To15Percentage == null) this.redCardFrom0To15Percentage = "-";
        if (this.redCardFrom16To30Percentage == null) this.redCardFrom16To30Percentage = "-";
        if (this.redCardFrom31To45Percentage == null) this.redCardFrom31To45Percentage = "-";
        if (this.redCardFrom46To60Percentage == null) this.redCardFrom46To60Percentage = "-";
        if (this.redCardFrom61To75Percentage == null) this.redCardFrom61To75Percentage = "-";
        if (this.redCardFrom76To90Percentage == null) this.redCardFrom76To90Percentage = "-";
        if (this.redCardFrom91To105Percentage == null) this.redCardFrom91To105Percentage = "-";
        if (this.redCardFrom106To120Percentage == null) this.redCardFrom106To120Percentage = "-";
        if (this.yellowCardFrom0To15Percentage == null) this.yellowCardFrom0To15Percentage = "-";
        if (this.yellowCardFrom16To30Percentage == null) this.yellowCardFrom16To30Percentage = "-";
        if (this.yellowCardFrom31To45Percentage == null) this.yellowCardFrom31To45Percentage = "-";
        if (this.yellowCardFrom46To60Percentage == null) this.yellowCardFrom46To60Percentage = "-";
        if (this.yellowCardFrom61To75Percentage == null) this.yellowCardFrom61To75Percentage = "-";
        if (this.yellowCardFrom76To90Percentage == null) this.yellowCardFrom76To90Percentage = "-";
        if (this.yellowCardFrom91To105Percentage == null) this.yellowCardFrom91To105Percentage = "-";
        if (this.yellowCardFrom106To120Percentage == null) this.yellowCardFrom106To120Percentage = "-";
        if (this.goalsScoredAverageHome == null) this.goalsScoredAverageHome = "-";
        if (this.goalsScoredAverageAway == null) this.goalsScoredAverageAway = "-";
        if (this.goalsScoredFrom0To15Percentage == null) this.goalsScoredFrom0To15Percentage = "-";
        if (this.goalsScoredFrom16To30Percentage == null) this.goalsScoredFrom16To30Percentage = "-";
        if (this.goalsScoredFrom31To45Percentage == null) this.goalsScoredFrom31To45Percentage = "-";
        if (this.goalsScoredFrom46To60Percentage == null) this.goalsScoredFrom46To60Percentage = "-";
        if (this.goalsScoredFrom61To75Percentage == null) this.goalsScoredFrom61To75Percentage = "-";
        if (this.goalsScoredFrom76To90Percentage == null) this.goalsScoredFrom76To90Percentage = "-";
        if (this.goalsScoredFrom91To105Percentage == null) this.goalsScoredFrom91To105Percentage = "-";
        if (this.goalsScoredFrom106To120Percentage == null) this.goalsScoredFrom106To120Percentage = "-";
        if (this.goalsConcededAverageHome == null) this.goalsConcededAverageHome = "-";
        if (this.goalsConcededAverageAway == null) this.goalsConcededAverageAway = "-";
        if (this.goalsConcededFrom0To15Percentage == null) this.goalsConcededFrom0To15Percentage = "-";
        if (this.goalsConcededFrom16To30Percentage == null) this.goalsConcededFrom16To30Percentage = "-";
        if (this.goalsConcededFrom31To45Percentage == null) this.goalsConcededFrom31To45Percentage = "-";
        if (this.goalsConcededFrom46To60Percentage == null) this.goalsConcededFrom46To60Percentage = "-";
        if (this.goalsConcededFrom61To75Percentage == null) this.goalsConcededFrom61To75Percentage = "-";
        if (this.goalsConcededFrom76To90Percentage == null) this.goalsConcededFrom76To90Percentage = "-";
        if (this.goalsConcededFrom91To105Percentage == null) this.goalsConcededFrom91To105Percentage = "-";
        if (this.goalsConcededFrom106To120Percentage == null) this.goalsConcededFrom106To120Percentage = "-";



        if (this.biggestStreakDraws == null) this.biggestStreakDraws = 0;
        if (this.biggestStreakLoses == null) this.biggestStreakLoses = 0;
        if (this.biggestStreakWins == null) this.biggestStreakWins = 0;
        if (this.biggestGoalsForHome == null) this.biggestGoalsForHome = 0;
        if (this.biggestGoalsForAway == null) this.biggestGoalsForAway = 0;
        if (this.biggestGoalsAgainstHome == null) this.biggestGoalsAgainstHome = 0;
        if (this.biggestGoalsAgainstAway == null) this.biggestGoalsAgainstAway = 0;
        if (this.redCardFrom0To15Total == null) this.redCardFrom0To15Total = 0;
        if (this.redCardFrom16To30Total == null) this.redCardFrom16To30Total = 0;
        if (this.redCardFrom31To45Total == null) this.redCardFrom31To45Total = 0;
        if (this.redCardFrom46To60Total == null) this.redCardFrom46To60Total = 0;
        if (this.redCardFrom61To75Total == null) this.redCardFrom61To75Total = 0;
        if (this.redCardFrom76To90Total == null) this.redCardFrom76To90Total = 0;
        if (this.redCardFrom91To105Total == null) this.redCardFrom91To105Total = 0;
        if (this.redCardFrom106To120Total == null) this.redCardFrom106To120Total = 0;
        if (this.yellowCardFrom0To15Total == null) this.yellowCardFrom0To15Total = 0;
        if (this.yellowCardFrom16To30Total == null) this.yellowCardFrom16To30Total = 0;
        if (this.yellowCardFrom31To45Total == null) this.yellowCardFrom31To45Total = 0;
        if (this.yellowCardFrom46To60Total == null) this.yellowCardFrom46To60Total = 0;
        if (this.yellowCardFrom61To75Total == null) this.yellowCardFrom61To75Total = 0;
        if (this.yellowCardFrom76To90Total == null) this.yellowCardFrom76To90Total = 0;
        if (this.yellowCardFrom91To105Total == null) this.yellowCardFrom91To105Total = 0;
        if (this.yellowCardFrom106To120Total == null) this.yellowCardFrom106To120Total = 0;
        if (this.cleanSheetsTotal == null) this.cleanSheetsTotal = 0;
        if (this.failedToScoreTotal == null) this.failedToScoreTotal = 0;
        if (this.goalsScoredAverageTotal == null) this.goalsScoredAverageTotal = "-";
        if (this.goalsScoredFrom0To15Total == null) this.goalsScoredFrom0To15Total = 0;
        if (this.goalsScoredFrom16To30Total == null) this.goalsScoredFrom16To30Total = 0;
        if (this.goalsScoredFrom31To45Total == null) this.goalsScoredFrom31To45Total = 0;
        if (this.goalsScoredFrom46To60Total == null) this.goalsScoredFrom46To60Total = 0;
        if (this.goalsScoredFrom61To75Total == null) this.goalsScoredFrom61To75Total = 0;
        if (this.goalsScoredFrom76To90Total == null) this.goalsScoredFrom76To90Total = 0;
        if (this.goalsScoredFrom91To105Total == null) this.goalsScoredFrom91To105Total = 0;
        if (this.goalsScoredFrom106To120Total == null) this.goalsScoredFrom106To120Total = 0;
        if (this.goalsScoredTotalHome == null) this.goalsScoredTotalHome = 0;
        if (this.goalsScoredTotalAway == null) this.goalsScoredTotalAway = 0;
        if (this.goalsConcededAverageTotal == null) this.goalsConcededAverageTotal = "-";
        if (this.goalsConcededFrom0To15Total == null) this.goalsConcededFrom0To15Total = 0;
        if (this.goalsConcededFrom16To30Total == null) this.goalsConcededFrom16To30Total = 0;
        if (this.goalsConcededFrom31To45Total == null) this.goalsConcededFrom31To45Total = 0;
        if (this.goalsConcededFrom46To60Total == null) this.goalsConcededFrom46To60Total = 0;
        if (this.goalsConcededFrom61To75Total == null) this.goalsConcededFrom61To75Total = 0;
        if (this.goalsConcededFrom76To90Total == null) this.goalsConcededFrom76To90Total = 0;
        if (this.goalsConcededFrom91To105Total == null) this.goalsConcededFrom91To105Total = 0;
        if (this.goalsConcededFrom106To120Total == null) this.goalsConcededFrom106To120Total = 0;
        if (this.goalsConcededTotalHome == null) this.goalsConcededTotalHome = 0;
        if (this.goalsConcededTotalAway == null) this.goalsConcededTotalAway = 0;
        if (this.drawsTotal == null) this.drawsTotal = 0;
        if (this.losesTotal == null) this.losesTotal = 0;
        if (this.playedTotal == null) this.playedTotal = 0;
        if (this.winsTotal == null) this.winsTotal = 0;
        if (this.penaltiesTotal == null) this.penaltiesTotal = 0;
        if (this.penaltiesScoredTotal == null) this.penaltiesScoredTotal = "-";
        if (this.penaltiesMissedTotal == null) this.penaltiesMissedTotal = 0;
        if (this.drawsHome == null) this.drawsHome = 0;
        if (this.drawsAway == null) this.drawsAway = 0;
        if (this.losesHome == null) this.losesHome = 0;
        if (this.losesAway == null) this.losesAway = 0;
        if (this.playedHome == null) this.playedHome = 0;
        if (this.playedAway == null) this.playedAway = 0;
        if (this.winsHome == null) this.winsHome = 0;
        if (this.winsAway == null) this.winsAway = 0;
        if (this.seasonYear == null) this.seasonYear = 0;
        if (this.cleanSheetsHome == null) this.cleanSheetsHome = 0;
        if (this.cleanSheetsAway == null) this.cleanSheetsAway = 0;
        if (this.failedToScoreHome == null) this.failedToScoreHome = 0;
        if (this.failedToScoreAway == null) this.failedToScoreAway = 0;


        }

    }

