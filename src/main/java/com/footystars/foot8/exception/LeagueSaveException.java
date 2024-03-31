package com.footystars.foot8.exception;

public class LeagueSaveException extends RuntimeException {
    public LeagueSaveException(String message) {
        super(message);
    }

    public LeagueSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}