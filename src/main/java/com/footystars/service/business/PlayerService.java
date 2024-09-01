package com.footystars.service.business;

import com.footystars.model.api.Players;
import com.footystars.persistence.entity.Player;
import com.footystars.persistence.mapper.PlayerMapper;
import com.footystars.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;
    private final ZodiacService zodiacService;

    @Cacheable(value = "players", key = "#playerId + '-' + #leagueId + '-' + #season + '-' + #clubId")
    public Optional<Player> findByIdLeagueIdSeasonAndClubId(Long playerId, Long leagueId, Integer season, Long clubId) {
        return playerRepository.findByPlayerIdLeagueIdSeasonAndClubId(playerId, leagueId, season, clubId);
    }

    @CachePut(value = "players", key = "#player.id + '-' + #player.statistics.league.leagueId + '-' + #player.statistics.league.season + '-' + #player.statistics.club.clubId")
    @Transactional
    public void updatePlayer(@NotNull Players.PlayerDto playerDto, @NotNull Player player) {
        playerMapper.partialUpdate(playerDto, player);
        playerRepository.save(player);
    }

    @CachePut(value = "players", key = "#playerEntity.info.playerId + '-' + #playerEntity.statistics.league.leagueId + '-' + #playerEntity.statistics.league.season + '-' + #playerEntity.statistics.club.clubId")
    @Transactional
    public void createPlayer(@NotNull Players.PlayerDto playerDto) {
        var playerEntity = playerMapper.toEntity(playerDto);
        var birthDate = playerEntity.getInfo().getBirth().getBirthDate();
        if (birthDate != null) {
            var zodiac = zodiacService.getZodiacSign(birthDate);
            playerEntity.getInfo().setZodiac(String.valueOf(zodiac));
        }
        playerRepository.save(playerEntity);
    }

}
