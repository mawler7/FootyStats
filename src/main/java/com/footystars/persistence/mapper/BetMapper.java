package com.footystars.persistence.mapper;

import com.footystars.model.dto.bet.BetDto;
import com.footystars.model.entity.Bet;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BetMapper {
    Bet toEntity(BetDto betDto);

    BetDto toDto(Bet bet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Bet partialUpdate(BetDto betDto, @MappingTarget Bet bet);
}