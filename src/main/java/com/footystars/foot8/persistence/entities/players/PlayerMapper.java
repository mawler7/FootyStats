package com.footystars.foot8.persistence.entities.players;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    Player playerToEntity(com.footystars.foot8.api.model.players.model.player.Player player);

    com.footystars.foot8.api.model.players.model.player.Player entityToPlayer(Player playerEntity);

}