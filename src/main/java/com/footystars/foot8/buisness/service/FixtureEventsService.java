package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.fixtures.events.event.FixtureEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.FIXTURE;

@Service
@RequiredArgsConstructor
public class FixtureEventsService {
    private final FixtureService fixtureService;

    public void fetchFixtureEvents(@NotNull List<FixtureEvent> events, @NotNull Map<String, String> params) {
        var fixtureId = Long.valueOf(params.get(FIXTURE));
        var optionalFixture = fixtureService.findById(fixtureId);
        if (optionalFixture.isPresent()) {
            var fixture = optionalFixture.get();
            fixture.setEvents(events);
            fixtureService.save(fixture);
        }
    }
}



