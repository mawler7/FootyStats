package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.standings.StandingsApi;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.SEASON;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final CompetitionService competitionService;

    public void fetchStandings(@NotNull StandingsApi standingsApi, @NotNull Map<String, String> params) {
        var leagueId = Long.valueOf(params.get(LEAGUE));
        var season = Integer.parseInt(params.get(SEASON));
        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, season);

        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();

            var response = standingsApi.getResponse();
            response.forEach(r -> {
                var standings = r.getLeague().getStandings()
                        .stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

                competition.setStandings(standings);
                competitionService.save(competition);
            });
        }
    }
}