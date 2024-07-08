package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.lineups.lineup.LineupApi;
import com.footystars.foot8.api.model.lineups.lineup.startxi.StartXI;
import com.footystars.foot8.api.model.lineups.lineup.substitutes.Substitutes;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.business.model.entity.Lineup;
import com.footystars.foot8.business.model.entity.Player;
import com.footystars.foot8.mapper.LineupMapper;
import com.footystars.foot8.repository.LineupRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineupFactory {

    private final LineupRepository lineupRepository;
    private final PlayerService playerService;
    private final FixtureService fixtureService;
    private final TeamService teamService;
    private final LineupMapper lineupMapper;

    private final Logger logger = LoggerFactory.getLogger(LineupFactory.class);

    @Transactional
    public void createOrUpdateLineup(@NotNull Fixture fixture, @NotNull LineupApi lineupApi) {
        var clubId = lineupApi.getClub().getId();
        var existingLineup = findExistingLineup(fixture, clubId);

        existingLineup.ifPresentOrElse(lineup ->
                updateLineup(lineup, lineupApi), () ->
                createLineup(fixture, lineupApi)
        );

    }


    public Optional<Lineup> findExistingLineup(@NotNull Fixture fixture, @NotNull Long clubId) {
        return fixture.getLineups().stream()
                .filter(lineup -> lineup.getTeam().getClubId().equals(clubId))
                .findFirst();
    }

    @Transactional
    public void createLineup(@NotNull Fixture fixture, @NotNull LineupApi lineupApi) {
        var clubId = lineupApi.getClub().getId();
        var leagueId = fixture.getHomeTeam().getSeason().getLeague().getId();
        var year = fixture.getHomeTeam().getSeason().getYear();

        var optionalTeam = teamService.getByClubIdLeagueIdAndYear(clubId, leagueId, year);

        if (optionalTeam.isPresent()) {
            var team = optionalTeam.get();
            var lineup = lineupMapper.toEntity(lineupApi);

            lineup.setTeam(team);
            lineup.setStartXI(mapStartXI(lineupApi.getStartXI()));
            lineup.setSubstitutes(mapSubstitutes(lineupApi.getSubstitutes()));

            lineupRepository.save(lineup);
            fixture.getLineups().add(lineup);
            fixtureService.save(fixture);
            logger.info("Created lineup for fixture: {}", fixture.getId());
        }
    }

    @Transactional
    public void updateLineup(@NotNull Lineup lineup, @NotNull LineupApi lineupApi) {
        lineup.setFormation(lineupApi.getFormation());
        lineup.getStartXI().clear();
        lineup.getStartXI().addAll(mapStartXI(lineupApi.getStartXI()));
        lineup.getSubstitutes().clear();
        lineup.getSubstitutes().addAll(mapSubstitutes(lineupApi.getSubstitutes()));
        lineupRepository.save(lineup);
    }

    private Set<Player> mapStartXI(@NotNull Set<StartXI> startXIS) {
        return startXIS.stream()
                .map(startXI -> playerService.findById(startXI.getPlayer().getId()).orElse(null))
                .collect(Collectors.toSet());
    }

    private Set<Player> mapSubstitutes(@NotNull Set<Substitutes> substitutes) {
        return substitutes.stream()
                .map(substitute -> playerService.findById(substitute.getPlayer().getId()).orElse(null))
                .collect(Collectors.toSet());
    }
}