package com.example.foot8.exception;

public class SaveLeagueException extends RuntimeException {
    public SaveLeagueException(String s, Throwable cause) {
        super("Cannot save league!", cause);
    }
}
