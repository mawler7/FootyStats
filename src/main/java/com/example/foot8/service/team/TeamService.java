package com.example.foot8.service.team;

import com.example.foot8.api.teams.statistics.model.TeamStatistics;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.entities.teams.TeamEntity;
import com.example.foot8.persistence.entities.teams.statistics.TeamStatisticsMapper;
import com.example.foot8.persistence.repository.TeamRepository;
import com.example.foot8.service.league.LeagueService;
import com.example.foot8.utils.League;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final LeagueService leagueService;
    private final TeamRepository teamRepository;
    private final TeamStatisticsMapper teamStatisticsMapper;


//    @Transactional
//    public void saveOrUpdateTeamInfo(@NotNull TeamInfo teamInfo, CountryEntity country) {
//        Optional<TeamEntity> team = teamRepository.findById(teamInfo.getId());
//        if (team.isPresent()) {
//            TeamEntity teamEntity = team.get();
//            if (teamEntity.getCountry() == null) {
//                teamEntity.setCountry(country);
//                teamRepository.save(teamEntity);
//            }
//        } else {
//
//            TeamEntity entity = TeamEntity.builder()
//                    .id(teamInfo.getId())
//                    .name(teamInfo.getName())
//                    .code(teamInfo.getCode())
//                    .country(country)
//                    .founded(teamInfo.getFounded())
//                    .national(teamInfo.isNational())
//                    .logo(teamInfo.getLogo())
//                    .venue(venue)
//                    .build();
//            teamRepository.save(entity);
//        }
//
//
//    }

    @Transactional
    public void saveOrUpdateTeamStats(@NotNull TeamStatistics teamStatistics) {

        Long leagueId = teamStatistics.getLeague().getLeagueId();
        Long teamId = teamStatistics.getTeam().getTeamId();

        if (teamId != null) {
            Optional<TeamEntity> teamEntity = findById(teamId);

            if (teamEntity.isPresent()) {
                TeamEntity team = teamEntity.get();
                if (leagueId != null) {
                    Optional<LeagueEntity> leagueEntity = leagueService.findById(leagueId);
                    if (leagueEntity.isPresent()) {
                        LeagueEntity league = leagueEntity.get();
                        team.setLeague(league);
                        team.setStatistics(List.of(teamStatisticsMapper.toEntity(teamStatistics)));
                        teamRepository.save(team);

                    }

                }
            }

        }
    }

    public Optional<TeamEntity> findById(Long id) {
        return teamRepository.findById(id);
    }

    public void save(TeamEntity teamEntity) {
        teamRepository.save(teamEntity);
    }

    public Map<String, List<Long>> getTeamIdsForTopLeagues() {

        Map<String, List<Long>> teams = new HashMap<>();

        List<String> leagueIds = League.getAllIds();

        leagueIds.forEach(id -> {
            getTeamIdsByLeagueId(id).forEach(teamId -> {
                teams.computeIfAbsent(id, k -> new ArrayList<>()).add(teamId);
            });
        });
            return teams;
        }

        public List<Long> getTeamIdsByLeagueId (String leagueId){
            return teamRepository.findTeamIdsByLeagueId(Long.valueOf(leagueId));

        }

    }
