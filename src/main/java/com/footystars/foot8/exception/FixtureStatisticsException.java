package com.footystars.foot8.exception;

public class FixtureStatisticsException extends RuntimeException {

    public FixtureStatisticsException(Exception e, String s) {
        super(s, e);
    }

}
