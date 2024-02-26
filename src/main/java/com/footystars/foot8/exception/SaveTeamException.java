package com.footystars.foot8.exception;

public class SaveTeamException extends RuntimeException {
    public SaveTeamException(String s, Throwable cause) {
        super("Cannot save team!", cause);
    }
}
