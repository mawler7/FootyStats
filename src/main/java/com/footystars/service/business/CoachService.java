package com.footystars.service.business;

import com.footystars.model.api.Coaches;
import com.footystars.model.entity.Coach;
import com.footystars.model.entity.Team;
import com.footystars.persistence.mapper.CoachMapper;
import com.footystars.persistence.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing coaches and assigning them to teams.
 */
@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final TeamService teamService;

    private static final Logger log = LoggerFactory.getLogger(CoachService.class);

    /**
     * Fetches or updates a coach and assigns them to the appropriate team.
     *
     * @param coachDto DTO object representing the coach data retrieved from the API.
     * @param clubId   The ID of the club the coach belongs to.
     */
    @Transactional
    public void fetchCoach(@NotNull Coaches.CoachDto coachDto, Long clubId) {
        if (coachDto.getCoachId() == null) {
            log.warn("CoachDto does not contain coachId, skipping.");
            return;
        }

        var existingCoach = findById(coachDto.getCoachId()).orElse(null);
        if (existingCoach != null) {
            coachMapper.partialUpdate(coachDto, existingCoach);
            coachRepository.save(existingCoach);
            updateTeamsWithCoach(existingCoach, coachDto, clubId);
        } else {
            var newCoach = coachMapper.toEntity(coachDto);
            var assignedTeam = getTeamWithLatestCareer(coachDto);
            if (assignedTeam != null) {
                newCoach = coachRepository.save(newCoach);
                assignedTeam.setCoach(newCoach);
                teamService.save(assignedTeam);
            }
        }
    }

    /**
     * Updates teams associated with a given coach.
     *
     * @param coach    The coach entity.
     * @param coachDto DTO containing coach career data.
     * @param clubId   The club ID associated with the coach.
     */
    private void updateTeamsWithCoach(Coach coach, Coaches.CoachDto coachDto, Long clubId) {
        var teams = teamService.getCurrentSeasonTeamsByClubId(clubId);
        var latestTeam = getTeamWithLatestCareer(coachDto);

        teams.forEach(team -> {
            if (team.equals(latestTeam)) {
                team.setCoach(coach);
            } else if (team.getCoach() != null && team.getCoach().getId().equals(coach.getId())) {
                team.setCoach(null);
            }
            teamService.save(team);
        });
    }

    /**
     * Returns the team where the coach most recently worked (based on the latest start date).
     *
     * @param coachDto DTO containing the coach's career history.
     * @return The most recent team the coach should be assigned to, or null if not found.
     */
    private Team getTeamWithLatestCareer(@NotNull Coaches.CoachDto coachDto) {
        return coachDto.getCareer().stream()
                .filter(career -> career.getStartDate() != null)
                .max(Comparator.comparing(
                        career -> parseDate(career.getStartDate()),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .flatMap(latestCareer -> teamService.getCurrentSeasonTeamsByClubId(latestCareer.getTeam().getTeamId())
                        .stream()
                        .findFirst())
                .orElse(null);
    }

    /**
     * Parses a date string in the format `yyyy-MM-dd` into a {@link LocalDate} object.
     *
     * @param date The string representation of the date.
     * @return The parsed date or `null` if the format is invalid.
     */
    @Nullable
    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format: {}", date);
            return null;
        }
    }

    /**
     * Finds a coach by their ID.
     *
     * @param id The ID of the coach.
     * @return An {@link Optional} containing the coach if found, otherwise empty.
     */
    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    /**
     * Saves a coach to the database.
     *
     * @param coach The coach entity to be saved.
     */
    public void save(Coach coach) {
        coachRepository.save(coach);
    }

    /**
     * Retrieves all coaches from the database.
     *
     * @return A list of all registered coaches.
     */
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }
}
