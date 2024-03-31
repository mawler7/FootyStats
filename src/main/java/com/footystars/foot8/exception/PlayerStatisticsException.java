package com.footystars.foot8.exception;

public class PlayerStatisticsException extends RuntimeException {

    public PlayerStatisticsException(Exception e, String s) {
        super("Cannot save player!", e);
    }
}

