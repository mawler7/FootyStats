package com.example.foot8.service;


import com.example.foot8.buisness.match.response.Response;
import com.example.foot8.persistence.entities.LeagueEntity;
import com.example.foot8.persistence.entities.MatchEntity;
import com.example.foot8.persistence.entities.TeamEntity;
import com.example.foot8.persistence.entities.VenueEntity;
import com.example.foot8.persistence.repository.LeagueRepository;
import com.example.foot8.persistence.repository.MatchRepository;
import com.example.foot8.persistence.repository.TeamRepository;
import com.example.foot8.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final VenueRepository venueRepository;

    public void saveMatch(Response response) {
        final var fixtureDto = response.getFixture();
        final var leagueDto = response.getLeague();
        final var teamsDto = response.getTeams();

        if (fixtureDto.getId() != null) {
            LeagueEntity leagueEntity = leagueRepository.findById(leagueDto.getId()).orElse(null);
            if (leagueEntity == null) {
                leagueEntity = new LeagueEntity(leagueDto);
                leagueRepository.save(leagueEntity);
            }
        }


        if (fixtureDto.getVenue().getId() != null) {
            VenueEntity venueEntity = venueRepository.findById(fixtureDto.getVenue().getId()).orElse(null);
            if (venueEntity == null) {
                venueEntity = new VenueEntity(fixtureDto.getVenue());
                venueRepository.save(venueEntity);
            }
        }

        if (teamsDto.getHome().getId() != null) {
            TeamEntity homeTeamEntity = teamRepository.findById(teamsDto.getHome().getId()).orElse(null);
            if (homeTeamEntity == null) {
                homeTeamEntity = new TeamEntity(teamsDto.getHome());
                teamRepository.save(homeTeamEntity);
            }
        }


        if (teamsDto.getAway().getId() != null) {
            TeamEntity awayTeamEntity = teamRepository.findById(teamsDto.getAway().getId()).orElse(null);
            if (awayTeamEntity == null) {
                awayTeamEntity = new TeamEntity(teamsDto.getAway());
                teamRepository.save(awayTeamEntity);
            }

        }

        Optional<MatchEntity> existingMatch = matchRepository.findByFixtureId(response.getFixture().getId());
        if (existingMatch.isPresent()) {
            MatchEntity match = existingMatch.get();
            if (response.getGoals() != null) {
                match.setHomeTeamGoals(response.getGoals().getHome());
                match.setAwayTeamGoals(response.getGoals().getAway());
                match.setHalftimeHomeTeamGoals(response.getScore().getHalftime().getHome());
                match.setHalftimeAwayTeamGoals(response.getScore().getHalftime().getAway());
                match.setExtratimeHomeTeamGoals(response.getScore().getExtratime().getHome());
                match.setExtratimeAwayTeamGoals(response.getScore().getExtratime().getAway());
                match.setPenaltyHomeTeamGoals(response.getScore().getPenalty().getHome());
                match.setPenaltyAwayTeamGoals(response.getScore().getPenalty().getAway());
                match.setAwayTeamWinner(response.getTeams().getAway().getWinner());
                match.setHomeTeamWinner(response.getTeams().getHome().getWinner());
                matchRepository.save(match);
            }
        } else {

            MatchEntity matchEntity = MatchEntity.builder()
                    .fixtureId(response.getFixture().getId())
                    .referee(response.getFixture().getReferee())
                    .timezone(response.getFixture().getTimezone())
                    .date(response.getFixture().getDate())
                    .timestamp(response.getFixture().getTimestamp())
                    .venueId(response.getFixture().getVenue().getId())
                    .venueName(response.getFixture().getVenue().getName())
                    .venueCity(response.getFixture().getVenue().getCity())
                    .leagueName(response.getLeague().getName())
                    .leagueCountry(response.getLeague().getCountry())
                    .leagueLogo(response.getLeague().getLogo())
                    .leagueFlag(response.getLeague().getFlag())
                    .leagueSeason(response.getLeague().getSeason())
                    .leagueRound(response.getLeague().getRound())
                    .homeTeamId(response.getTeams().getHome().getId())
                    .homeTeamName(response.getTeams().getHome().getName())
                    .homeTeamLogo(response.getTeams().getHome().getLogo())
                    .homeTeamWinner(response.getTeams().getHome().getWinner())
                    .awayTeamId(response.getTeams().getAway().getId())
                    .awayTeamName(response.getTeams().getAway().getName())
                    .awayTeamLogo(response.getTeams().getAway().getLogo())
                    .awayTeamWinner(response.getTeams().getAway().getWinner())
                    .homeTeamGoals(response.getGoals().getHome())
                    .awayTeamGoals(response.getGoals().getAway())
                    .halftimeHomeTeamGoals(response.getScore().getHalftime().getHome())
                    .halftimeAwayTeamGoals(response.getScore().getHalftime().getAway())
                    .extratimeHomeTeamGoals(response.getScore().getExtratime().getHome())
                    .extratimeAwayTeamGoals(response.getScore().getExtratime().getAway())
                    .penaltyHomeTeamGoals(response.getScore().getPenalty().getHome())
                    .penaltyAwayTeamGoals(response.getScore().getPenalty().getAway())
                    .build();
            matchRepository.save(matchEntity);
        }
    }

}
