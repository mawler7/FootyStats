package com.example.foot8.service.league;

import com.example.foot8.api.league.model.LeagueResponseData;
import com.example.foot8.exception.SaveLeagueException;
import com.example.foot8.service.country.CountryService;
import com.example.foot8.service.season.SeasonService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LeagueResponseService {
    private final CountryService countryService;
    private final SeasonService seasonService;
    private final LeagueService leagueService;

    @Async
    public void saveResponseDataAsync(LeagueResponseData leagueResponseData, String leagueType) {
        CompletableFuture.runAsync(() -> {
            try {
                saveResponseData(leagueResponseData, leagueType);
            } catch (Exception e) {
                throw new SaveLeagueException("Cannot save league with id: " + leagueResponseData.getLeague().getId()
                        + " and name: " + leagueResponseData.getLeague().getName() + " with message: "
                        + e.getMessage(), e);
            }
        });
    }

    public void saveResponseData(@NotNull LeagueResponseData leagueResponseData, @NotNull String leagueType) {
        var country = leagueResponseData.getCountry();
        var league = leagueResponseData.getLeague();
        var seasons = leagueResponseData.getSeasons();

        var countryEntity = countryService.saveOrUpdateCountry(country);
        var leagueEntity = leagueService.saveOrUpdateLeague(league, countryEntity, leagueType);
        seasonService.saveOrUpdateSeasons(seasons, leagueEntity);
    }
}
