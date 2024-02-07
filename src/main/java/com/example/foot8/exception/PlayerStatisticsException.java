package com.example.foot8.exception;

public class PlayerStatisticsException extends Exception {

    public PlayerStatisticsException(String s, Exception e) {
        super("Cannot save player!", e);
    }
}

