package com.example.foot8.service;

import com.example.foot8.buisness.match.response.Response;
import com.example.foot8.exception.SaveLeagueException;
import com.example.foot8.exception.SaveMatchException;
import com.example.foot8.exception.SaveTeamException;
import com.example.foot8.exception.SaveVenueException;
import com.example.foot8.persistence.entities.LeagueEntity;
import com.example.foot8.persistence.entities.MatchEntity;
import com.example.foot8.persistence.entities.TeamEntity;
import com.example.foot8.persistence.entities.VenueEntity;
import com.example.foot8.persistence.repository.LeagueRepository;
import com.example.foot8.persistence.repository.MatchRepository;
import com.example.foot8.persistence.repository.TeamRepository;
import com.example.foot8.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchRepository matchRepository;
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final VenueService venueService;

    public void saveMatch(@NotNull Response response) {
        try {
            saveLeague(response);
        } catch (Exception e) {
            throw new SaveLeagueException(e);
        }
        try {
            saveVenue(response);
        } catch (Exception e) {
            throw new SaveVenueException(e);
        }
        try {
            saveHomeTeam(response);
        } catch (Exception e) {
            throw new SaveTeamException(e);
        }
        try {
            saveAwayTeam(response);
        } catch (Exception e) {
            throw new SaveTeamException(e);
        }
        try {
            updateMatch(response);
        } catch (Exception e) {
            throw new SaveMatchException(e);
        }
    }

    private void updateMatch(@NotNull Response response) {
        Optional<MatchEntity> existingMatch = matchRepository.findByFixtureId(response.getFixture().getId());

        if (existingMatch.isPresent() && shouldUpdateMatch(existingMatch.get(), response)) {
            MatchEntity match = existingMatch.get();
            updateMatchFields(match, response);
            matchRepository.save(match);
            log.info("Match " + response.getFixture().getId() + " has been updated");
        } else if (existingMatch.isEmpty()) {
            MatchEntity match = createNewMatch(response);
            matchRepository.save(match);
            log.info("Match " + response.getFixture().getId() + " has been saved");
        }
    }

    private boolean shouldUpdateMatch(MatchEntity match, Response response) {
        return (response.getGoals().getHome() != null &&
                response.getGoals().getAway() != null) &&
                (match.getHomeTeamGoals() == null && match.getAwayTeamGoals() == null);
    }

    private MatchEntity createNewMatch(@NotNull Response response) {
        return MatchEntity.builder()
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
    }

    private void updateMatchFields(MatchEntity match, @NotNull Response response) {
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
    }


    private void saveAwayTeam(@NotNull Response response) {
        final var teamsDto = response.getTeams();
        if (teamsDto.getAway().getId() != null) {
            TeamEntity awayTeamEntity = teamService.findById(teamsDto.getAway().getId()).orElse(null);
            if (awayTeamEntity == null) {
                awayTeamEntity = new TeamEntity(teamsDto.getAway());
                teamService.save(awayTeamEntity);
                log.info("Team " + response.getTeams().getAway().getName() + " saved successfully!");
            }
        }
    }

    private void saveHomeTeam(@NotNull Response response) {
        final var teamsDto = response.getTeams();
        if (teamsDto.getHome().getId() != null) {
            TeamEntity homeTeamEntity = teamService.findById(teamsDto.getHome().getId()).orElse(null);
            if (homeTeamEntity == null) {
                homeTeamEntity = new TeamEntity(teamsDto.getHome());
                teamService.save(homeTeamEntity);
                log.info("Team " + response.getTeams().getHome().getName() + " saved successfully!");
            }
        }
    }

    private void saveVenue(@NotNull Response response) {
        final var venueDto = response.getFixture().getVenue();
        if (venueDto.getId() != null) {
            VenueEntity venueEntity = venueService.findById(venueDto.getId()).orElse(null);
            if (venueEntity == null) {
                venueEntity = new VenueEntity(venueDto);
                venueService.save(venueEntity);
                log.info("Venue " + response.getFixture().getVenue().getName() + " saved successfully!");
            }
        }
    }

    private void saveLeague(@NotNull Response response) {
        final var leagueDto = response.getLeague();
        if (leagueDto.getId() != null) {
            LeagueEntity leagueEntity = leagueService.findById(leagueDto.getId()).orElse(null);
            if (leagueEntity == null) {
                leagueEntity = new LeagueEntity(leagueDto);
                leagueService.save(leagueEntity);
                log.info("League " + response.getLeague().getName() + " saved successfully!");
            }
        }
    }

}
