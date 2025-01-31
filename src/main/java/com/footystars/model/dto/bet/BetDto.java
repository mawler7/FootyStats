package com.footystars.model.dto.bet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Getter
@Setter
@Builder
public class BetDto implements Serializable {
  private String bookmakerName;
  private String betName;
  private String value;
  private double odd;
  private ZonedDateTime lastUpdated;
}