package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entity.coachs.Coach;
import com.footystars.foot8.persistence.entity.coachs.CoachDto;
import com.footystars.foot8.persistence.entity.coachs.CoachMapper;
import com.footystars.foot8.persistence.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final TeamService teamService;


    @Transactional
    public void fetchCoach(@NotNull CoachDto coachDto) {
        var coachId = coachDto.getId();

        if (coachId != null) {
            var optionalCoach = findById(coachId);

            if (optionalCoach.isEmpty()) {
                var clubId = coachDto.getTeam().getId();
                var optionalTeam = teamService.getCurrentSeasonTeamByClubId(clubId);
                if(optionalTeam.isPresent()) {
                    var team = optionalTeam.get();
                    var coach = coachMapper.toEntity(coachDto);
                    coach.setTeam(team);
                    var savedCoach = coachRepository.save(coach);
                    team.setCoach(savedCoach);
                    teamService.save(team);
                }
            } else {
                var coach = optionalCoach.get();
                coachMapper.partialUpdate(coachDto, coach);
            }
        }
    }

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }
}
