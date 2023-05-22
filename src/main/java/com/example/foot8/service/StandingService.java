package com.example.foot8.service;

import com.example.foot8.buisness.standing.StandingData;
import com.example.foot8.buisness.standing.StandingDataWrapper;
import com.example.foot8.constants.League;
import com.example.foot8.persistence.entities.MatchEntity;
import com.example.foot8.persistence.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class StandingService {

    private final MatchRepository matchRepository;

    public List<StandingDataWrapper> getStandingsForAllLeagues(){
        Integer currentSeason = getCurrentSeason();
        return Arrays.stream(League.values())
                .map(league -> {
                    List<StandingData> standingList = calculateStandings(league.getLeagueName(), currentSeason);
                    return StandingDataWrapper.builder()
                            .leagueName(league.getLeagueName())
                            .season(currentSeason)
                            .standingData(standingList)
                            .build();
                })
                .toList();
    }


    public StandingDataWrapper getStandingsForLeague(String leagueName){
        Integer currentSeason = getCurrentSeason();
        List<StandingData> standingList = calculateStandings(leagueName, currentSeason);
                    return StandingDataWrapper.builder()
                            .leagueName(leagueName)
                            .season(currentSeason)
                            .standingData(standingList)
                            .build();
    }


    public List<StandingData> calculateStandings(String leagueName, Integer season) {
        List<MatchEntity> matches = matchRepository.findByLeagueNameAndLeagueSeasonAndStatus(leagueName, season, "FT");
        Map<Long, StandingData> standings = new HashMap<>();

        matches.forEach(match -> {
            StandingData homeTeamStanding = standings.computeIfAbsent(match.getHomeTeamId(),
                    id -> StandingData.builder().teamName(match.getHomeTeamName()).build());
            StandingData awayTeamStanding = standings.computeIfAbsent(match.getAwayTeamId(),
                    id -> StandingData.builder().teamName(match.getAwayTeamName()).build());

            homeTeamStanding.setMatchesPlayed(homeTeamStanding.getMatchesPlayed() + 1);
            awayTeamStanding.setMatchesPlayed(awayTeamStanding.getMatchesPlayed() + 1);

            int homeTeamGoals = match.getHomeTeamGoals();
            int awayTeamGoals = match.getAwayTeamGoals();

            homeTeamStanding.setGoalDifference(homeTeamStanding.getGoalDifference() + homeTeamGoals - awayTeamGoals);
            awayTeamStanding.setGoalDifference(awayTeamStanding.getGoalDifference() + awayTeamGoals - homeTeamGoals);

            if (homeTeamGoals > awayTeamGoals) {
                homeTeamStanding.setWins(homeTeamStanding.getWins() + 1);
                homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 3);
                awayTeamStanding.setLosses(awayTeamStanding.getLosses() + 1);
            } else if (homeTeamGoals < awayTeamGoals) {
                awayTeamStanding.setWins(awayTeamStanding.getWins() + 1);
                awayTeamStanding.setPoints(awayTeamStanding.getPoints() + 3);
                homeTeamStanding.setLosses(homeTeamStanding.getLosses() + 1);
            } else {
                homeTeamStanding.setDraws(homeTeamStanding.getDraws() + 1);
                homeTeamStanding.setPoints(homeTeamStanding.getPoints() + 1);
                awayTeamStanding.setDraws(awayTeamStanding.getDraws() + 1);
                awayTeamStanding.setPoints(awayTeamStanding.getPoints() + 1);
            }

            homeTeamStanding.setGoalsFor(homeTeamStanding.getGoalsFor() + homeTeamGoals);
            homeTeamStanding.setGoalsAgainst(homeTeamStanding.getGoalsAgainst() + awayTeamGoals);
            awayTeamStanding.setGoalsFor(awayTeamStanding.getGoalsFor() + awayTeamGoals);
            awayTeamStanding.setGoalsAgainst(awayTeamStanding.getGoalsAgainst() + homeTeamGoals);
        });

        List<StandingData> standingsList = new ArrayList<>(standings.values());

        standingsList.sort((s1, s2) -> {
            int pointsDiff = s2.getPoints() - s1.getPoints();
            if (pointsDiff != 0) {
                return pointsDiff;
            }
            int goalDiff = s2.getGoalDifference() - s1.getGoalDifference();
            if (goalDiff != 0) {
                return goalDiff;
            }
            return s2.getGoalsFor() - s1.getGoalsFor();
        });

        IntStream.range(0, standingsList.size()).forEach(i -> standingsList.get(i).setPosition(i + 1));

        return standingsList;
    }
    private Integer getCurrentSeason() {
        return matchRepository.findMaxLeagueSeason();
    }

}
