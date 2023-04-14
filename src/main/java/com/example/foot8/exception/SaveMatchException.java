package com.example.foot8.exception;

public class SaveMatchException extends RuntimeException {
    public SaveMatchException(Throwable cause) {
        super("Cannot save match!", cause);
    }
}
