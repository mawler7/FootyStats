package com.footystars.service.business;

import com.footystars.model.api.Standings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Service responsible for managing league standings.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StandingsService {

    private final LeagueService leagueService;

    /**
     * Fetches and updates league standings for a given league and season.
     * <p>
     * The existing standings are cleared and replaced with the new data provided in the API response.
     * </p>
     *
     * @param standings A list of standings grouped by divisions or groups within the league.
     * @param params    A map containing league and season identifiers.
     */
    @Transactional
    public void fetchStandings(@NotNull List<ArrayList<Standings.StandingApi.StandingLeague.Standing>> standings,
                               @NotNull Map<String, String> params) {

        var leagueId = Long.valueOf(params.get(LEAGUE));
        var season = Integer.parseInt(params.get(SEASON));

        var optionalLeague = leagueService.findByLeagueIdAndSeason(leagueId, season);
        if (optionalLeague.isPresent()) {
            var league = optionalLeague.get();

            league.getStandings().clear();

            standings.forEach(groupStandings -> {
                List<Standings.StandingApi.StandingLeague.Standing> flattenedStandings =
                        groupStandings.stream().toList();
                league.getStandings().addAll(flattenedStandings);
            });

            leagueService.save(league);
            log.info(STANDINGS_LEAGUE_SEASON_FETCHED, leagueId, season);
        }
    }

    /**
     * Retrieves the standings for the current season of a given league.
     * <p>
     * If the league's current season exists, it fetches the standings for that season.
     * If no standings are found, an empty list is returned.
     * </p>
     *
     * @param leagueId The ID of the league.
     * @return A list of standings for the league in the current season, or an empty list if not found.
     */
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
