package com.footystars.exception;


public class PlayerStatisticsException extends RuntimeException {

    public PlayerStatisticsException(String s, Exception e) {
        super(s, e);
    }
}

