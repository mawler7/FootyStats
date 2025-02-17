package com.footystars.model.entity;

import com.footystars.model.api.Fixtures;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fixtures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedNativeQuery(
        name = "FixturePlayer.findLastMatchesByPlayerId",
        query = "SELECT " +
                "f.date AS matchDate, " +
                "f.league_id AS leagueId, " +
                "f.league_name AS leagueName, " +
                "f.logo AS leagueLogo, " +
                "f.home_name AS homeTeamName, " +
                "f.away_name AS awayTeamName, " +
                "f.home_logo AS homeTeamLogo, " +
                "f.away_logo AS awayTeamLogo, " +
                "f.home AS home, " +
                "f.away AS away, " +
                "fps.rating AS rating, " +
                "fps.minutes AS minutes, " +
                "fps.goals_total AS goals, " +
                "fps.assists AS assists, " +
                "fps.yellow AS yellowCards, " +
                "fps.red AS redCards " +
                "FROM fixtures_players fp " +
                "JOIN fixtures f ON fp.fixture_id = f.id " +
                "JOIN fixture_players_stats fps ON fp.id = fps.fixture_player_id " +
                "WHERE fp.player_id = :playerId " +
                "ORDER BY f.date DESC",
        resultSetMapping = "PlayerLastMatchDtoMapping"
)
@SqlResultSetMapping(
        name = "PlayerLastMatchDtoMapping",
        classes = @ConstructorResult(
                targetClass = com.footystars.model.dto.player.PlayerLastMatchDto.class,
                columns = {
                        @ColumnResult(name = "matchDate", type = ZonedDateTime.class),
                        @ColumnResult(name = "leagueId", type = Long.class),
                        @ColumnResult(name = "leagueName", type = String.class),
                        @ColumnResult(name = "leagueLogo", type = String.class),
                        @ColumnResult(name = "homeTeamName", type = String.class),
                        @ColumnResult(name = "awayTeamName", type = String.class),
                        @ColumnResult(name = "homeTeamLogo", type = String.class),
                        @ColumnResult(name = "awayTeamLogo", type = String.class),
                        @ColumnResult(name = "home", type = Integer.class),
                        @ColumnResult(name = "away", type = Integer.class),
                        @ColumnResult(name = "rating", type = String.class),
                        @ColumnResult(name = "minutes", type = Integer.class),
                        @ColumnResult(name = "goals", type = Integer.class),
                        @ColumnResult(name = "assists", type = Integer.class),
                        @ColumnResult(name = "yellowCards", type = Integer.class),
                        @ColumnResult(name = "redCards", type = Integer.class)
                }
        )
)
public class Fixture implements Serializable {

    @Id
    private Long id;

    @Column(name = "last_updated", columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime lastUpdated;

    @Embedded
    private Fixtures.FixtureDto.FixtureInfo info;
    @Embedded
    private Fixtures.FixtureDto.LeagueDto league;
    @Embedded
    private Fixtures.FixtureDto.Goals goals;
    @Embedded
    private Fixtures.FixtureDto.Score score;
    @Embedded
    private Fixtures.FixtureDto.Teams teams;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Bet> bets = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Lineup> lineups = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FixturePlayer> players = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FixtureEvent> events = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FixtureStatistic> statistics = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.lastUpdated = ZonedDateTime.now();
    }

    @Column(name = "predictions_updated")
    private Boolean predictionsUpdated = false;

}
