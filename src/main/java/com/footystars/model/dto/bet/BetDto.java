package com.footystars.model.dto.bet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Getter
@Setter
@Builder
public class BetDto implements Serializable {
  private Long fixtureId;
  private String bookmakerName;
  private String betName;
  private String value;
  private double odd;

  public BetDto(String bookmakerName, String betName, String value, double odd) {
    this.bookmakerName = bookmakerName;
    this.betName = betName;
    this.value = value;
    this.odd = odd;
  }

  public BetDto(Long fixtureId, String bookmakerName, String betName, String value, double odd) {
    this.fixtureId = fixtureId;
    this.bookmakerName = bookmakerName;
    this.betName = betName;
    this.value = value;
    this.odd = odd;
  }
}