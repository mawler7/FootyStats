package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.lineups.lineup.LineupApi;
import com.footystars.foot8.api.model.lineups.lineup.startxi.StartXI;
import com.footystars.foot8.api.model.lineups.lineup.substitutes.Substitutes;
import com.footystars.foot8.buisness.model.entity.Lineup;
import com.footystars.foot8.buisness.model.entity.Player;
import com.footystars.foot8.mapper.PlayerMapper;
import com.footystars.foot8.repository.FixtureRepository;
import com.footystars.foot8.repository.LineupRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.FIXTURE;

@Service
@RequiredArgsConstructor
public class LineupsService {

    private final LineupRepository lineupRepository;
    private final FixtureRepository fixtureRepository;
    private final PlayerMapper playerMapper;
    private final CoachService coachService;
    private final ClubService clubService;
    private final Logger logger = LoggerFactory.getLogger(LineupsService.class);

    @Transactional
    public void fetchFixtureLineups(@NotNull List<LineupApi> lineups, @NotNull Map<String, String> params) {
        var fixtureId = Long.valueOf(params.get(FIXTURE));
        var optionalFixture = fixtureRepository.findById(fixtureId);

        optionalFixture.ifPresent(fixture -> lineups.forEach(lineupApi -> {
            var start = new HashSet<Player>();
            var subs = new HashSet<Player>();
            var clubId = lineupApi.getClub().getId();
            var coachId = lineupApi.getCoach().getId();

            if (clubId != null && coachId != null) {
                var optionalClub = clubService.findById(clubId);

                if (optionalClub.isPresent()) {
                    var club = optionalClub.get();
                    var optionalCoach = coachService.findById(coachId);

                    if (optionalCoach.isPresent()) {
                        var coach = optionalCoach.get();
                        var startXIApi = lineupApi.getStartXI();
                        var substitutes = lineupApi.getSubstitutes();

                        startXIApi.stream().map(StartXI::getPlayer)
                                .forEach(p -> {
                                    var player = playerMapper.lineupPlayerToEntity(p);
                                    start.add(player);
                                });

                        substitutes.stream().map(Substitutes::getPlayer)
                                .forEach(p -> {
                                    var player = playerMapper.lineupPlayerToEntity(p);
                                    subs.add(player);
                                });

                        var fixtureLineup = Lineup.builder()
                                .startXI(start)
                                .fixture(fixture)
                                .club(club)
                                .coach(coach)
                                .formation(lineupApi.getFormation())
                                .substitutes(subs)
                                .build();

                        var savedLineup = lineupRepository.save(fixtureLineup);
                        fixture.getLineups().add(savedLineup);
                        fixtureRepository.save(fixture);
                    }
                    logger.info("Fixture lineups updated for fixture ID: {}", fixtureId);
                }
            }

        }));
    }
}

