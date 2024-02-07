package com.example.foot8.service.team;

import com.example.foot8.api.teams.info.model.TeamInfo;
import com.example.foot8.api.teams.info.response.TeamInfoResponseData;
import com.example.foot8.api.teams.statistics.model.TeamStatistics;
import com.example.foot8.api.venue.model.VenueDto;
import com.example.foot8.exception.LeagueException;
import com.example.foot8.persistence.entities.countries.CountryEntity;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.entities.teams.TeamEntity;
import com.example.foot8.persistence.entities.teams.statistics.TeamStatisticsEntity;
import com.example.foot8.persistence.entities.teams.statistics.TeamStatisticsMapper;
import com.example.foot8.persistence.entities.venues.VenueEntity;
import com.example.foot8.persistence.entities.venues.VenueMapper;
import com.example.foot8.service.country.CountryService;
import com.example.foot8.service.league.LeagueService;
import com.example.foot8.service.venue.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamResponseService {

    private final TeamService teamService;
    private final TeamStatisticsService teamStatisticsService;
    private final LeagueService leagueService;
    private final CountryService countryService;
    private final VenueService venueService;
    private final TeamStatisticsMapper teamStatisticsMapper;
    private final VenueMapper venueMapper;


    public void saveTeamInfoResponseData(@NotNull TeamInfoResponseData responseData, String leagueId) {

        VenueDto venueDto = responseData.getVenue();
        TeamInfo teamInfo = responseData.getTeam();

        CountryEntity countryEntity = saveOrUpdateCountry(teamInfo.getCountry());
        VenueEntity venueEntity = saveOrUpdateVenue(venueDto, countryEntity);
        LeagueEntity leagueEntity = updateCountryForLeague(leagueId, countryEntity);

        Optional<TeamEntity> team = teamService.findById(teamInfo.getId());
        if (team.isEmpty()) {
            TeamEntity teamEntity = TeamEntity.builder()
                    .id(teamInfo.getId())
                    .name(teamInfo.getName())
                    .code(teamInfo.getCode())
                    .country(countryEntity)
                    .founded(teamInfo.getFounded())
                    .national(teamInfo.isNational())
                    .logo(teamInfo.getLogo())
                    .league(leagueEntity)
                    .venue(venueEntity)
                    .build();
            teamService.save(teamEntity);
        } else {
            TeamEntity teamEntity = team.get();
            if (teamEntity.getCountry() == null) {
                teamEntity.setCountry(countryEntity);
                teamService.save(teamEntity);
            }
            if (teamEntity.getVenue() == null) {
                teamEntity.setVenue(venueEntity);
                teamService.save(teamEntity);
            }

        }

    }
    public void saveTeamStatsResponseData(@NotNull TeamStatistics teamStatistics) {
        Long teamId = teamStatistics.getTeam().getTeamId();

        Optional<TeamEntity> optionalTeam = teamService.findById(teamId);
        if (optionalTeam.isPresent()) {
            TeamEntity teamEntity = optionalTeam.get();
            if (teamEntity.getLeague() == null) {
                Long leagueId = teamStatistics.getLeague().getLeagueId();
                if (leagueId != null) {
                    Optional<LeagueEntity> optionalLeague = leagueService.findById(leagueId);
                    optionalLeague.ifPresent(teamEntity::setLeague);
                }
            }
            TeamStatisticsEntity statisticsEntity = teamStatisticsMapper.toEntity(teamStatistics);
            statisticsEntity.setTeam(teamEntity);
            teamStatisticsService.save(statisticsEntity);

            teamService.save(teamEntity);
        }
    }


    @NotNull
    private LeagueEntity updateCountryForLeague(String leagueId, CountryEntity countryEntity) {
        Optional<LeagueEntity> league = leagueService.findById(Long.valueOf(leagueId));
        if (league.isEmpty()) {
            throw new LeagueException("Could not find league with id: " + leagueId);
        } else {
            LeagueEntity leagueEntity = league.get();

            if (leagueEntity.getCountry() == null) {
                leagueEntity.setCountry(countryEntity);
                leagueService.save(leagueEntity);
            }
            return leagueEntity;
        }
    }


    private VenueEntity saveOrUpdateVenue(@NotNull VenueDto venueDto, CountryEntity countryEntity) {
        if (venueDto.getId() != null) {
            Optional<VenueEntity> venue = venueService.findById(venueDto.getId());
            if (venue.isPresent()) {
                VenueEntity venueEntity = venue.get();
                if (venueEntity.getCountry() == null) {
                    venueEntity.setCountry(countryEntity.getName());
                    return venueService.saveVenue(venueEntity);
                }
            } else {
                VenueEntity venueEntity = venueMapper.toEntity(venueDto);
                venueEntity.setCountry(countryEntity.getName());
                return venueService.saveVenue(venueEntity);
            }
        }
        return venueMapper.toEntity(venueDto);
    }


    private CountryEntity saveOrUpdateCountry(String countryName) {
        return countryService.findByName(countryName).orElseGet(() -> {
            CountryEntity entity = CountryEntity.builder()
                    .name(countryName)
                    .build();
            return countryService.save(entity);
        });
    }

}
