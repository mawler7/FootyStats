package com.footystars.service.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixtureStatisticsService {
    private final FixtureService fixtureService;

//    public void fetchFixtureStatistics(@NotNull Set<Fixtures.FixtureDto.Statistics.Statistic> statisticsApi, @NotNull Map<String, String> params) {
//
//        var statistics = new HashSet<Fixtures.FixtureDto.Statistics.Statistic>();
//        statisticsApi.forEach(s -> {
//            var team = s.getTeam();
//            var statisticList = s.getStats();
//            statisticList.forEach(statistic -> {
//                statistic.setTeam(team);
//                statistics.add(statistic);
//            });
//            var fixtureId = Long.valueOf(params.get(FIXTURE));
//            var optionalFixture = fixtureService.findById(fixtureId);
//            if (optionalFixture.isPresent()) {
//                var fixture = optionalFixture.get();
//                fixture.setStatistics(statistics);
//                fixtureService.save(fixture);
//            }
//        });
//    }


}
