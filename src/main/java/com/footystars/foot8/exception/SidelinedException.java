package com.footystars.foot8.exception;

import java.io.IOException;

public class SidelinedException extends RuntimeException {
    public SidelinedException(Exception e, String s) {
        super(s, e);
    }
}
