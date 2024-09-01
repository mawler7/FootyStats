package com.footystars.service.business;

import com.footystars.model.api.Coaches;
import com.footystars.persistence.entity.Coach;
import com.footystars.persistence.mapper.CoachMapper;
import com.footystars.persistence.repository.CoachRepository;
import com.footystars.utils.LogsNames;
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

    @Transactional
    public void fetchCoach(@NotNull Coaches.CoachDto coachDto, @NotNull Long clubId) {
        var coachId = coachDto.getId();
        if (coachId != null) {
            var optionalCoach = findById(coachId);
            if (optionalCoach.isPresent()) {
                var coach = optionalCoach.get();
                coachMapper.partialUpdate(coachDto, coach);
                coachRepository.save(coach);
            } else {
                var teams = teamService.getCurrentSeasonTeamsByClubId(clubId);
                if(!teams.isEmpty()) {
                    teams.forEach(t -> {
                        if (t.getCoach() == null) {
                            var coach = coachMapper.toEntity(coachDto);
                            var savedCoach = coachRepository.save(coach);
                            t.setCoach(savedCoach);
                            teamService.save(t);
                        }
                    });
                }

            }
        }
    }

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    public void save(Coach coach) {
        coachRepository.save(coach);
    }


}