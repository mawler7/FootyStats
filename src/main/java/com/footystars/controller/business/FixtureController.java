package com.footystars.controller.business;

import com.footystars.model.dto.fixture.*;
import com.footystars.model.dto.team.TeamComeBacksDto;
import com.footystars.model.dto.team.TeamCorrectScoresDto;
import com.footystars.model.dto.team.TeamHTDrawsDto;
import com.footystars.service.business.FixtureService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * REST controller for managing football fixtures.
 * Provides endpoints to retrieve match data, statistics, predictions, and updates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/fixture")
@CrossOrigin(origins = "http://localhost:3000")
public class FixtureController {

    private final FixtureService fixtureService;

    /**
     * Retrieves fixtures for a specific date.
     *
     * @param date the date for which fixtures should be retrieved.
     * @return a list of fixtures for the given date.
     */
    @GetMapping("/{date}")
    public ResponseEntity<List<MatchDto>> getFixturesByDate(@PathVariable String date) {
        var todayFixtures = fixtureService.getMatchesByDate(date);
        return ResponseEntity.ok(todayFixtures);
    }

    /**
     * Retrieves filtered fixtures based on optional parameters.
     *
     * @param startDate the start date (optional).
     * @param endDate   the end date (optional).
     * @param leagueId  the league ID (optional).
     * @param homeName  the home team name (optional).
     * @param awayName  the away team name (optional).
     * @param page      the page number for pagination (default 0).
     * @param size      the number of results per page (default 10).
     * @return a paginated list of filtered fixtures.
     */
    @GetMapping
    public ResponseEntity<Page<DBViewMatchDto>> getFixtures(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long leagueId,
            @RequestParam(required = false) String homeName,
            @RequestParam(required = false) String awayName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DBViewMatchDto> fixtures = fixtureService.getFixturesFiltered(startDate, endDate, leagueId, homeName, awayName, pageable);
        return ResponseEntity.ok(fixtures);
    }

    /**
     * Retrieves fixture details along with average betting odds.
     *
     * @param id the fixture ID.
     * @return the match details including odds.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getFixtureWithAverageBets(@PathVariable Long id) {
        try {
            MatchDetailsDto matchDetailsDto = fixtureService.getMatchDetailsWithAverageBets(id);
            return ResponseEntity.ok(matchDetailsDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Match not found for ID: " + id);
        }
    }

    @GetMapping("predict/id/{id}")
    public ResponseEntity<?> getFixtureData(@PathVariable Long id) {
        try {
            MatchDetailsDto matchInfo = fixtureService.getMatchDetailsDto(id);
            return ResponseEntity.ok(matchInfo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Match not found for ID: " + id);
        }
    }


    /**
     * Retrieves completed fixtures for a specific league in the current season.
     *
     * @param leagueId the league ID.
     * @return a list of completed fixtures.
     */
    @GetMapping("/current/{leagueId}")
    public ResponseEntity<List<MatchDto>> getCurrentSeasonFixturesByLeagueIdEnded(@PathVariable Long leagueId) {
        var todayFixtures = fixtureService.findCurrentSeasonFixturesByLeagueIdEnded(leagueId);
        return ResponseEntity.ok(todayFixtures);
    }

    /**
     * Retrieves head-to-head (H2H) match history between two teams.
     *
     * @param homeId the home team ID.
     * @param awayId the away team ID.
     * @return H2H match data.
     */
    @GetMapping("/h2h/{homeId}/{awayId}")
    public ResponseEntity<H2HDto>  getH2HMatches(@PathVariable Long homeId, @PathVariable Long awayId) {
        var  h2hDto = fixtureService.getHeadToHeadMatches(homeId, awayId);
        return ResponseEntity.ok(h2hDto);
    }

    /**
     * Retrieves fixtures for a specific club in the current season.
     *
     * @param id the club ID.
     * @return a list of fixtures for the club.
     */
    @GetMapping("/team/id/{id}")
    public ResponseEntity<List<MatchDto>> getCurrentSeasonFixturesByClubId(@PathVariable Long id) {
        var todayFixtures = fixtureService.findCurrentSeasonFixturesByClubId(id);
        return ResponseEntity.ok(todayFixtures);
    }

    /**
     * Retrieves teams that made comebacks from halftime losing positions.
     *
     * @return a list of comeback statistics.
     */
    @GetMapping("/ht-ft")
    public ResponseEntity<List<TeamComeBacksDto>> getHTLAndFTW() {
        var drawStatistics = fixtureService.getTeamsWithComebacks();
        return ResponseEntity.ok(drawStatistics);
    }

    /**
     * Retrieves teams with the most correct score results.
     *
     * @return a list of correct score statistics.
     */
    @GetMapping("/score")
    public ResponseEntity<List<TeamCorrectScoresDto>> getCorrectScore() {
        var drawStatistics = fixtureService.getTeamsMostCorrectScores();
        return ResponseEntity.ok(drawStatistics);
    }

    /**
     * Retrieves teams with the most halftime draws.
     *
     * @return a list of halftime draw statistics.
     */
    @GetMapping("/ht")
    public ResponseEntity<List<TeamHTDrawsDto>> getHalfTimeDraws() {
        var drawStatistics = fixtureService.getTeamsMostHTDraws();
        return ResponseEntity.ok(drawStatistics);
    }

    /**
     * Saves a prediction to a JSON file.
     *
     * @param data the prediction data, including home and away team names.
     * @return a response indicating success or failure.
     */
    @PostMapping("/savePrediction")
    public ResponseEntity<?> savePrediction(@RequestBody Map<String, Object> data) {
        return fixtureService.savePredictionToFile(data);
    }

}
