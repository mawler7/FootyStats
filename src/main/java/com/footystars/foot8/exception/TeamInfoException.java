package com.footystars.foot8.exception;


public class TeamInfoException extends RuntimeException {
    public TeamInfoException(String message) {
    }

    public TeamInfoException(String message, Throwable cause) {
        super(message, cause);
    }

}
