package com.footystars.foot8.exception;

public class LeagueException extends Exception {
    public LeagueException(Exception e, String message) {
        super(message);
    }
}
