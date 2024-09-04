package com.footystars.exception;

public class TeamInfoException extends RuntimeException {
    public TeamInfoException(Exception e) {
        super("Failed to fetch team information", e);
    }
}
