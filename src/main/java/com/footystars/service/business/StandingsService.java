package com.footystars.service.business;

import com.footystars.model.api.Standings;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.footystars.utils.LogsNames.STANDINGS_LEAGUE_SEASON_FETCHED;
import static com.footystars.utils.ParameterName.LEAGUE;
import static com.footystars.utils.ParameterName.SEASON;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final LeagueService leagueService;

    private final Logger log = LoggerFactory.getLogger(StandingsService.class);

    @Transactional
    public void fetchStandings(@NotNull List<ArrayList<Standings.StandingApi.StandingLeague.Standing>> standings,
                               @NotNull Map<String, String> params) {

        var leagueId = Long.valueOf(params.get(LEAGUE));
        var season = Integer.parseInt(params.get(SEASON));

        var optionalLeague = leagueService.findByLeagueIdAndSeason(leagueId, season);
        if (optionalLeague.isPresent()) {
            var league = optionalLeague.get();

            List<Standings.StandingApi.StandingLeague.Standing> flattenedStandings =
                    standings.stream()
                            .flatMap(List::stream)
                            .toList();
            league.getStandings().clear();
            league.getStandings().addAll(flattenedStandings);
            leagueService.save(league);
            log.info(STANDINGS_LEAGUE_SEASON_FETCHED, leagueId, season);
        }
    }

    public List<Standings.StandingApi.StandingLeague.Standing> findByLeagueId(Long leagueId) {
        var currentSeason = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (currentSeason.isPresent()) {
            var season = currentSeason.get();
            var optionalLeague = leagueService.findByLeagueIdAndSeason(leagueId, season);
            if (optionalLeague.isPresent()) {
                var league = optionalLeague.get();
                return league.getStandings();
            }
        }
        return Collections.emptyList();
    }
}
