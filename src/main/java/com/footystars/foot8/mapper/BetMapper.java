package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.odds.bets.bet.BetApi;
import com.footystars.foot8.business.model.entity.Bet;
import com.footystars.foot8.business.model.dto.BetDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BetMapper {

    Bet toEntity(BetDto betDto);

    BetDto toDto(Bet bet);

    @Mapping(target = "bookmaker", ignore = true)
    BetDto apiToDto(BetApi betApi);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Bet partialUpdate(BetDto betDto, @MappingTarget Bet bet);
}