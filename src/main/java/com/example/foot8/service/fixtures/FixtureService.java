package com.example.foot8.service.fixtures;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixtureService {

//
//    private final FixtureRepository fixtureRepository;
//
//    private final LeagueService leagueService;
//    private final TeamService teamService;
//    private final VenueService venueService;
//
//    private static final String SUCCESS = " saved successfully!";
//
//
//    public void saveMatch(@NotNull FixtureDto response) {
//        try {
//            saveLeague(response);
//            saveVenue(response);
//            saveHomeTeam(response);
//            saveAwayTeam(response);
//            updateMatch(response);
//        } catch (Exception e) {
//            throw new SaveMatchException("Error while saving match", e);
//        }
//    }
//
//    public Optional<FixtureEntity> findByFixtureId(Long fixtureId) {
//        return fixtureRepository.findByFixtureId(fixtureId);
//    }
//
//    private void updateMatch(@NotNull FixtureDto response) {
//        Optional<FixtureEntity> existingMatch = fixtureRepository.findByFixtureId(response.getId());
//
//        if (existingMatch.isPresent() && shouldUpdateMatch(existingMatch.get(), response)) {
//            MatchEntity match = existingMatch.get();
//            updateMatchFields(match, response);
//            matchRepository.save(match);
//            log.info("Match " + response.getFixture().getId() + " has been updated");
//        } else if (existingMatch.isEmpty()) {
//            MatchEntity match = createNewMatch(response);
//            matchRepository.save(match);
//            log.info("Match " + response.getFixture().getId() + " has been saved");
//        }
//    }
//
//    private boolean shouldUpdateMatch(MatchEntity match, @NotNull MatchDto response) {
//        return response.getFixture().getStatus().getShortName().equals("FT") &&
//                (match.getStatus() == null || !match.getStatus().equals("FT"));
//    }
//
//    private MatchEntity createNewMatch(@NotNull MatchDto response) {
//        return MatchEntity.builder()
//                .fixtureId(response.getFixture().getId())
//                .referee(response.getFixture().getReferee())
//                .timezone(response.getFixture().getTimezone())
//                .date(response.getFixture().getDate())
//                .timestamp(response.getFixture().getTimestamp())
//                .venueId(response.getFixture().getVenue().getId())
//                .venueName(response.getFixture().getVenue().getName())
//                .venueCity(response.getFixture().getVenue().getCity())
//                .leagueName(response.getLeague().getName())
//                .leagueCountry(response.getLeague().getCountry())
//                .leagueSeason(response.getLeague().getSeason())
//                .leagueRound(response.getLeague().getRound())
//                .homeTeamId(response.getTeams().getHome().getId())
//                .homeTeamName(response.getTeams().getHome().getName())
//                .homeTeamWinner(response.getTeams().getHome().getWinner())
//                .awayTeamId(response.getTeams().getAway().getId())
//                .awayTeamName(response.getTeams().getAway().getName())
//                .awayTeamWinner(response.getTeams().getAway().getWinner())
//                .homeTeamGoals(response.getGoals().getHome())
//                .awayTeamGoals(response.getGoals().getAway())
//                .halftimeHomeTeamGoals(response.getScore().getHalftime().getHome())
//                .halftimeAwayTeamGoals(response.getScore().getHalftime().getAway())
//                .extratimeHomeTeamGoals(response.getScore().getExtratime().getHome())
//                .extratimeAwayTeamGoals(response.getScore().getExtratime().getAway())
//                .penaltyHomeTeamGoals(response.getScore().getPenalty().getHome())
//                .penaltyAwayTeamGoals(response.getScore().getPenalty().getAway())
//                .status(response.getFixture().getStatus().getShortName())
//                .build();
//    }
//
//    private void updateMatchFields(@NotNull MatchEntity match, @NotNull MatchDto response) {
//        match.setHomeTeamGoals(response.getGoals().getHome());
//        match.setAwayTeamGoals(response.getGoals().getAway());
//        match.setHalftimeHomeTeamGoals(response.getScore().getHalftime().getHome());
//        match.setHalftimeAwayTeamGoals(response.getScore().getHalftime().getAway());
//        match.setExtratimeHomeTeamGoals(response.getScore().getExtratime().getHome());
//        match.setExtratimeAwayTeamGoals(response.getScore().getExtratime().getAway());
//        match.setPenaltyHomeTeamGoals(response.getScore().getPenalty().getHome());
//        match.setPenaltyAwayTeamGoals(response.getScore().getPenalty().getAway());
//        match.setAwayTeamWinner(response.getTeams().getAway().getWinner());
//        match.setHomeTeamWinner(response.getTeams().getHome().getWinner());
//        match.setStatus(response.getFixture().getStatus().getShortName());
//        matchEntityMapper.toDto(match);
//    }
//
//
//    private void saveAwayTeam(@NotNull FixtureDto response) {
//
//        final var teamsDto = response.getTeams();
//        if (teamsDto.getAway().getId() != null) {
//            TeamDto awayDto = teamsDto.getAway();
//            TeamEntity awayTeamEntity = teamService.findById(awayDto.getId()).orElse(null);
//            if (awayTeamEntity == null) {
//                teamService.save(TeamEntity.builder()
//                        .id(awayDto.getId())
//                        .logo(awayDto.getLogo())
//                        .name(awayDto.getName())
//                        .build());
//            }
//        }
//    }
//
//    private void saveHomeTeam(@NotNull FixtureDto response) {
//
//        final var teamsDto = response.getTeams();
//        if (teamsDto.getHome().getId() != null) {
//            TeamDto homeDto = teamsDto.getHome();
//            TeamEntity homeTeamEntity = teamService.findById(homeDto.getId()).orElse(null);
//            if (homeTeamEntity == null) {
//                teamService.save(TeamEntity.builder()
//                        .id(homeDto.getId())
//                        .logo(homeDto.getLogo())
//                        .name(homeDto.getName())
//                        .build());
//            }
//
//            log.info("Team " + response.getTeams().getHome().getName() + SUCCESS);
//        }
//    }
//
//
//    private void saveVenue(@NotNull FixtureDto response) {
//        final var venueDto = response.getFixture().getVenue();
//        if (venueDto.getId() != null) {
//            VenueEntity venueEntity = venueService.findById(venueDto.getId()).orElse(null);
//            if (venueEntity == null) {
//                venueService.save(VenueEntity.builder()
//                        .city(venueDto.getCity())
//                        .name(venueDto.getName())
//                        .build());
//                log.info("Venue " + response.getFixture().getVenue().getName() + SUCCESS);
//            }
//        }
//    }
//
//    private void saveLeague(@NotNull FixtureDto response) {
//        final var leagueDto = response.getLeague();
//        if (leagueDto.getId() != null) {
//            LeagueEntity leagueEntity = leagueService.findById(leagueDto.getId()).orElse(null);
//            if (leagueEntity == null) {
//                leagueService.save(LeagueEntity.builder()
//                        .logo(leagueDto.getLogo())
//                        .flag(leagueDto.getFlag())
//                        .name(leagueDto.getName())
//                        .id(leagueDto.getId())
//                        .build());
//                log.info("League " + response.getLeague().getName() + SUCCESS);
//            }
//        }
//    }
//
//    public Map<String, List<TodayMatchDto>> findByTodayDate() {
//        LocalDateTime now = LocalDateTime.now();
//        ZoneId timeZone = ZoneId.of("Europe/Warsaw");
//        ZonedDateTime startOfDay = now.toLocalDate().atStartOfDay(timeZone);
//        ZonedDateTime endOfDay = now.toLocalDate().atTime(LocalTime.MAX).atZone(timeZone);
//
//        Optional<List<MatchEntity>> todayFixtures = matchRepository.findByDateBetween(
//                startOfDay.toInstant().toString(),
//                endOfDay.toInstant().toString()
//        );
//
//        return todayFixtures.orElse(Collections.emptyList())
//                .stream()
//                .map(this::mapToDto)
//                .filter(Objects::nonNull)
//                .collect(Collectors.groupingBy(TodayMatchDto::getLeagueName));
//    }
//
//    public Map<String, List<TodayMatchDto>> findByCurrentSeason() {
//        Integer currentSeason = getCurrentSeason();
//        List<MatchEntity> todayFixtures = matchRepository.findByCurrentSeason(currentSeason);
//
//        return todayFixtures.stream()
//                .map(this::mapToDto)
//                .filter(Objects::nonNull)
//                .collect(Collectors.groupingBy(TodayMatchDto::getLeagueName));
//    }
//
//    public Map<String, List<TodayMatchDto>> findMatchesByDate(String dateString) {
//        LocalDate date = LocalDate.parse(dateString);
//        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
//        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
//
//        Optional<List<MatchEntity>> todayFixtures = matchRepository.findByDateBetween(
//                startOfDay.toInstant(ZoneOffset.UTC).toString(),
//                endOfDay.toInstant(ZoneOffset.UTC).toString()
//        );
//
//        return todayFixtures.orElse(Collections.emptyList())
//                .stream()
//                .map(this::mapToDto)
//                .filter(Objects::nonNull)
//                .collect(Collectors.groupingBy(TodayMatchDto::getLeagueName));
//    }
//
//    public List<MatchEntity> findHeadToHead(Long homeTeamId, Long awayTeamId) {
//        List<MatchEntity> matches = matchRepository.findByHomeTeamIdAndAwayTeamId(homeTeamId, awayTeamId);
//        matches.addAll(matchRepository.findByHomeTeamIdAndAwayTeamId(awayTeamId, homeTeamId));
//        return matches.stream()
//                .filter(m -> m.getStatus() != null && m.getStatus().equals("FT"))
//                .sorted(Comparator.comparing(MatchEntity::getDate).reversed())
//                .toList();
//    }
//
//    public List<Map<String, Object>> getWinsByResult(int awayGoals, int homeGoals) {
//        return matchRepository.getWinsByResult(awayGoals, homeGoals);
//    }
//
//    public String getLeagueLogoByLeagueName(String leagueName) {
//        return leagueService.findLeagueByName(leagueName)
//                .map(LeagueEntity::getLogo)
//                .orElse(null);
//    }
//
//    private TodayMatchDto mapToDto(@NotNull MatchEntity matchEntity) {
//        LeagueEntity league = leagueService.findLeagueByName(matchEntity.getLeagueName()).orElse(null);
//        LocalDateTime matchDateTime = LocalDateTime.parse(
//                matchEntity.getDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).plusHours(1);
//
//        return TodayMatchDto.builder()
//                .id(matchEntity.getFixtureId())
//                .leagueId(Objects.requireNonNull(league).getId())
//                .leagueName(matchEntity.getLeagueName())
//                .leagueLogo(Objects.requireNonNull(league).getLogo())
//                .date(matchDateTime.toLocalDate().toString())
//                .time(matchDateTime.toLocalTime().toString())
//                .homeTeamLogo(teamService.findTeamLogoById(matchEntity.getHomeTeamId()))
//                .homeTeamName(matchEntity.getHomeTeamName())
//                .homeTeamGoals(matchEntity.getHomeTeamGoals())
//                .awayTeamGoals(matchEntity.getAwayTeamGoals())
//                .awayTeamLogo(teamService.findTeamLogoById(matchEntity.getAwayTeamId()))
//                .awayTeamName(matchEntity.getAwayTeamName())
//                .extratimeHomeTeamGoals(matchEntity.getExtratimeHomeTeamGoals())
//                .extratimeAwayTeamGoals(matchEntity.getExtratimeAwayTeamGoals())
//                .penaltyHomeTeamGoals(matchEntity.getPenaltyHomeTeamGoals())
//                .penaltyAwayTeamGoals(matchEntity.getPenaltyAwayTeamGoals())
//                .build();
//    }
//
//    List<MatchEntity> findByLeagueNameAndLeagueSeason(String leagueName, Integer leagueSeason, String status) {
//        return matchRepository.findByLeagueNameAndLeagueSeasonAndStatus(leagueName, leagueSeason, status);
//    }
//
//    public List<MatchEntity> findByCurrentSeasonAndHomeTeamIdOrAwayTeamId(Long homeTeamId, Long awayTeamId) {
//        Integer currentSeason = getCurrentSeason();
//        return matchRepository.findByLeagueSeasonAndHomeTeamIdOrAwayTeamId(currentSeason, homeTeamId, awayTeamId)
//                .stream()
//                .filter(m -> m.getStatus() != null && m.getStatus().equals("FT"))
//                .sorted(Comparator.comparing(MatchEntity::getDate).reversed())
//                .toList();
//    }
//
//    public List<MatchEntityDto> findByCurrentSeasonAndTeamId(Long id) {
//        Long currentSeason = Long.valueOf(getCurrentSeason());
//        List<MatchEntity> matchesList = matchRepository.getAllByLeagueSeasonAndTeamId(id, currentSeason);
//        return matchesList.stream()
//                .map(matchEntityMapper::toDto)
//                .collect(Collectors.toList());
//    }
}