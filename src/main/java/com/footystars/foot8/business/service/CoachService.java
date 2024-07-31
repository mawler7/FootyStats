package com.footystars.foot8.business.service;

import com.footystars.foot8.business.model.dto.CoachDto;
import com.footystars.foot8.business.model.entity.Coach;
import com.footystars.foot8.business.service.teams.TeamService;
import com.footystars.foot8.mapper.CoachMapper;
import com.footystars.foot8.repository.CoachRepository;
import com.footystars.foot8.utils.LogsNames;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final TeamService teamService;

    private final Logger logger = LoggerFactory.getLogger(CoachService.class);

    @Transactional
    public void fetchCoach(@NotNull CoachDto coachDto, @NotNull Long clubId) {
        var coachId = coachDto.getId();
        if (coachId != null) {
            var optionalCoach = findById(coachId);
            if (optionalCoach.isPresent()) {
                var coach = optionalCoach.get();
                coachMapper.partialUpdate(coachDto, coach);
                coachRepository.save(coach);
            } else {
                var teams = teamService.getCurrentSeasonTeamsByClubId(clubId);
                teams.forEach(t -> {
                    if (t.getCoach() == null) {
                        var coach = coachMapper.toEntity(coachDto);
                        var savedCoach = coachRepository.save(coach);
                        t.setCoach(savedCoach);
                        teamService.save(t);
                    }
                });
            }
            logger.info(LogsNames.COACH_FETCHED, coachDto.getName());
        }
    }

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    public void save(Coach coach) {
        coachRepository.save(coach);
    }
}