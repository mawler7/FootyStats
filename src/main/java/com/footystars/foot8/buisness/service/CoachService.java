package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.coaches.carrer.Careeer;
import com.footystars.foot8.persistence.entities.coachs.coach.Coach;
import com.footystars.foot8.persistence.entities.coachs.coach.CoachDto;
import com.footystars.foot8.persistence.entities.coachs.coach.CoachMapper;
import com.footystars.foot8.persistence.entities.coachs.season.CoachSeason;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import com.footystars.foot8.persistence.entities.teams.team.Team;
import com.footystars.foot8.persistence.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final CoachSeasonService coachSeasonService;
    private final TeamService teamService;
    private final TeamSeasonService teamSeasonService;
    private final LeagueSeasonService leagueSeasonService;

    public void fetchCoach(@NotNull CoachDto coachDto) {
//        var coachId = coachDto.getId();
//        if (coachId != null) {
//            var optionalCoach = findById(coachId);
//            if (optionalCoach.isEmpty()) {
//                var coachEntity = coachMapper.toEntity(coachDto);
//                var savedCoach = save(coachEntity);
//                var teamId = coachDto.getTeamDto().getTeamInfo().getTeamId();
//                var optionalTeam = teamService.findById(teamId);
//                if (optionalTeam.isPresent()) {
//                    Team team = optionalTeam.get();
//                    Optional<TeamSeason> optionalTeamSeason = teamSeasonService.findCurrentByTeamId(teamId);
//                    TeamSeason teamSeason = optionalTeamSeason.get();
//                    teamSeason.setCoachSeasons(C);
//                }
//                if (optionalTeamSeason.isPresent()) {
//                    var teamSeason = optionalTeamSeason.get();
//                    var coachSeason = CoachSeason.builder()
//                            .coach(savedCoach)
//                            .startDate(coachDto.getCarrer().stream()
//                                    .filter(carrer -> carrer.getEndDate() == null)
//                                    .map(Careeer::getStartDate).toString())
//                            .teamSeason(teamSeason)
//                            .build();
//                    var savedCoachSeason = coachSeasonService.save(coachSeason);
//                    var coachSeasons = teamSeason.getCoachSeasons();
//                    coachSeasons.add(savedCoachSeason);
//                    coachSeasonService.save(coachSeason);
//                    teamSeasonService.save(teamSeason);
//                }
//
//
//            }
//        }


    }

    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

}
