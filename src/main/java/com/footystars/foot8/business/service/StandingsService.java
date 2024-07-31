package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.standings.StandingsApi;
import com.footystars.foot8.api.model.standings.standing.Standing;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.SEASON;

@Service
@RequiredArgsConstructor
public class StandingsService {

    private final SeasonService seasonService;

    private final Logger log = LoggerFactory.getLogger(StandingsService.class);


    public void fetchStandings(@NotNull StandingsApi standingsApi, @NotNull Map<String, String> params) {
        var leagueId = Long.valueOf(params.get(LEAGUE));
        var year = Integer.parseInt(params.get(SEASON));
        var optionalSeason = seasonService.findByLeagueIdAndYear(leagueId, year);

        var seasonCoverage = optionalSeason.stream()
                .filter(s -> s.getCoverage().isStandings())
                .findFirst();
        if (seasonCoverage.isPresent()) {
            var season = seasonCoverage.get();
            var response = standingsApi.getResponse();

            response.forEach(r -> {
                var standings = r.getLeague().getStandings()
                        .stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

                season.setStandings(standings);
                seasonService.save(season);
                log.info("Saved standings for league {} and season {}", leagueId, season);
            });
        }
    }

    public List<Standing> getStandingsByLeagueId(Long leagueId) {
        return seasonService.findCurrentSeasonByLeagueId(leagueId).orElse(null).getStandings();
    }
}