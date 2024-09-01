package com.footystars.exception;

public class FixtureStatisticsException extends RuntimeException {

    public FixtureStatisticsException(Exception e, String s) {
        super(s, e);
    }

}
