package com.footystars.foot8.exception;

import static com.footystars.foot8.utils.LogsNames.PLAYER_STATISTICS_EXCEPTION;

public class PlayerStatisticsException extends RuntimeException {

    public PlayerStatisticsException(String s, Exception e) {
        super(s, e);
    }
}

