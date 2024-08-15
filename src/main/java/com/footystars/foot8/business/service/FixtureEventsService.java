package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.fixtures.events.event.FixtureEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FixtureEventsService {
    private final FixtureService fixtureService;

    public void fetchFixtureEvents(@NotNull Set<FixtureEvent> events, @NotNull Long fixtureId) {
        var optionalFixture = fixtureService.findById(fixtureId);
        if (optionalFixture.isPresent()) {
            var fixture = optionalFixture.get();
            fixture.setEvents(events);
            fixtureService.save(fixture);
        }
    }
}



