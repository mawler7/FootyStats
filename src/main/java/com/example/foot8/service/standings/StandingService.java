package com.example.foot8.service.standings;

import com.example.foot8.buisness.standing.StandingData;
import com.example.foot8.buisness.standing.StandingDataWrapper;
import com.example.foot8.exception.LeagueException;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.utils.League;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
@Slf4j
public class StandingService {

//    private final LeagueService leagueService;
//    private final TeamService teamService;
//    private final FixtureService matchService;
//
//
//
//    public List<StandingDataWrapper> getStandingsForAllLeagues() {
//        return Arrays.stream(League.values())
//                .map(league -> {
//                    List<StandingData> standingList = calculateStandings(league.getLeagueName(),matchService.getCurrentSeason());
//                    Optional<LeagueEntity> leagueByName = leagueService.findLeagueByName(league.getLeagueName());
//                    return StandingDataWrapper.builder()
//                            .leagueName(league.getLeagueName())
//                            .leagueId(leagueByName.map(LeagueEntity::getId).orElse(null))
//                            .leagueLogo(leagueByName.isPresent() ? leagueByName.get().getLogo() : "")
//                            .season(matchService.getCurrentSeason())
//                            .standingData(standingList)
//                            .build();
//                })
//                .toList();
//    }
//
//    public StandingDataWrapper getLeagueStandingsById(Long id) {
//        Optional<LeagueEntity> league = leagueService.findById(id);
//        if (league.isEmpty()) {
//            throw new LeagueException(String.valueOf(id));
//        }
//        List<StandingData> standingList = calculateStandings(league.get().getName(), matchService.getCurrentSeason());
//        return StandingDataWrapper.builder()
//                .leagueId(id)
//                .leagueName(league.get().getName())
//                .leagueLogo(league.get().getLogo())
//                .season(matchService.getCurrentSeason())
//                .standingData(standingList)
//                .build();
//    }
//
//    public List<StandingData> calculateStandings(String leagueName, Integer season) {
//
//        List<MatchEntity> matches = matchService.findByLeagueNameAndLeagueSeason(leagueName, season, "FT");
//        Map<Long, StandingData> standings = new HashMap<>();
//
//        matches.forEach(match -> {
//            StandingData homeTeamStanding = standings.computeIfAbsent(match.getHomeTeamId(),
//                    id -> StandingData.builder()
//                            .teamId(match.getHomeTeamId())
//                            .teamLogo(teamService.findTeamLogoById(id))
//                            .teamName(match.getHomeTeamName()).build());
//            StandingData awayTeamStanding = standings.computeIfAbsent(match.getAwayTeamId(),
//                    id -> StandingData.builder()
//                            .teamName(match.getAwayTeamName())
//                            .teamLogo(teamService.findTeamLogoById(id))
//                            .build());
//
//            homeTeamStanding.setMatchesPlayed(homeTeamStanding.getMatchesPlayed() + 1);
//            awayTeamStanding.setMatchesPlayed(awayTeamStanding.getMatchesPlayed() + 1);
//
//            int homeTeamGoals = match.getHomeTeamGoals();
//            int awayTeamGoals = match.getAwayTeamGoals();
//
//            homeTeamStanding.setGoalDifference(homeTeamStanding.getGoalDifference() + homeTeamGoals - awayTeamGoals);
//            awayTeamStanding.setGoalDifference(awayTeamStanding.getGoalDifference() + awayTeamGoals - homeTeamGoals);
//
//            if (homeTeamGoals > awayTeamGoals) {
//                homeTeamStanding.setWins(homeTeamStanding.getWins() + 1);
//                homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 3);
//                awayTeamStanding.setLosses(awayTeamStanding.getLosses() + 1);
//            } else if (homeTeamGoals < awayTeamGoals) {
//                awayTeamStanding.setWins(awayTeamStanding.getWins() + 1);
//                awayTeamStanding.setPoints(awayTeamStanding.getPoints() + 3);
//                homeTeamStanding.setLosses(homeTeamStanding.getLosses() + 1);
//            } else {
//                homeTeamStanding.setDraws(homeTeamStanding.getDraws() + 1);
//                homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 1);
//                awayTeamStanding.setDraws(awayTeamStanding.getDraws() + 1);
//                awayTeamStanding.setPoints(awayTeamStanding.getPoints() + 1);
//            }
//
//            homeTeamStanding.setGoalsFor(homeTeamStanding.getGoalsFor() + homeTeamGoals);
//            homeTeamStanding.setGoalsAgainst(homeTeamStanding.getGoalsAgainst() + awayTeamGoals);
//            awayTeamStanding.setGoalsFor(awayTeamStanding.getGoalsFor() + awayTeamGoals);
//            awayTeamStanding.setGoalsAgainst(awayTeamStanding.getGoalsAgainst() + homeTeamGoals);
//        });
//
//        List<StandingData> standingsList = new ArrayList<>(standings.values());
//
//        standingsList.sort((s1, s2) -> {
//            int pointsDiff = s2.getPoints() - s1.getPoints();
//            if (pointsDiff != 0) {
//                return pointsDiff;
//            }
//            int goalDiff = s2.getGoalDifference() - s1.getGoalDifference();
//            if (goalDiff != 0) {
//                return goalDiff;
//            }
//            return s2.getGoalsFor() - s1.getGoalsFor();
//        });
//
//        IntStream.range(0, standingsList.size()).forEach(i -> standingsList.get(i).setPosition(i + 1));
//
//        return standingsList;
//    }



}
