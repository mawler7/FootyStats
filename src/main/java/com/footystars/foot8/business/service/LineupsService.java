package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.lineups.lineup.LineupApi;
import com.footystars.foot8.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LineupsService {

    private final FixtureRepository fixtureRepository;
    private final LineupFactory lineupFactory;

    @Transactional
    public void fetchLineups(@NotNull List<LineupApi> lineups, @NotNull Long fixtureId) {
        var optionalFixture = fixtureRepository.findById(fixtureId);
        if (optionalFixture.isPresent()) {
            var fixture = optionalFixture.get();
            lineups.forEach(lineupApi -> lineupFactory.createOrUpdateLineup(fixture, lineupApi));
        }

    }
}