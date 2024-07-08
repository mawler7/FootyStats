package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.fixtures.statistics.Statistics;
import com.footystars.foot8.api.model.fixtures.statistics.statistic.Statistic;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.FIXTURE;

@Service
@RequiredArgsConstructor
public class FixtureStatisticsService {
    private final FixtureService fixtureService;

    public void fetchFixtureStatistics(@NotNull List<Statistics> statisticsApi, @NotNull Map<String, String> params) {

            var statistics = new ArrayList<Statistic>();
            statisticsApi.forEach(s -> {
                var team = s.getTeam();
                var statisticList = s.getStats();
                statisticList.forEach(statistic -> {
                    statistic.setTeam(team);
                    statistics.add(statistic);
                });
                var fixtureId = Long.valueOf(params.get(FIXTURE));
                var optionalFixture = fixtureService.findById(fixtureId);
                if (optionalFixture.isPresent()) {
                    var fixture = optionalFixture.get();
                    fixture.setStatistics(statistics);
                    fixtureService.save(fixture);
                }
            });
        }




}
