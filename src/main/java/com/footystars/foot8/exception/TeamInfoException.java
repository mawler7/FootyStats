package com.footystars.foot8.exception;


public class TeamInfoException extends RuntimeException {
    public TeamInfoException(Exception e) {
        super("Failed to fetch team information");
    }

    public TeamInfoException(String message, Throwable cause) {
        super(message, cause);
    }

}
