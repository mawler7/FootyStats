package com.footystars.foot8.api.service.requester;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Slf4j
public class CircuitBreaker {

    private enum State {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    private final int failureThreshold;
    private final Duration resetTimeout;
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private Instant lastFailureTime = Instant.now();
    private State state = State.CLOSED;

    public CircuitBreaker(int failureThreshold, Duration resetTimeout) {
        this.failureThreshold = failureThreshold;
        this.resetTimeout = resetTimeout;
    }

    public synchronized boolean allowRequest() {
        if (state == State.OPEN && Duration.between(lastFailureTime, Instant.now()).compareTo(resetTimeout) > 0) {
            state = State.HALF_OPEN;
            log.info("CircuitBreaker state changed to HALF_OPEN");
            return true;
        }

        if (state == State.OPEN) {
            log.info("CircuitBreaker state is OPEN, request not allowed");
            return false;
        }

        return true;
    }

    public synchronized void recordSuccess() {
        if (state == State.HALF_OPEN) {
            state = State.CLOSED;
            failureCount.set(0);
            log.info("CircuitBreaker state changed to CLOSED");
        }
    }

    public synchronized void recordFailure() {
        if (state == State.HALF_OPEN || state == State.CLOSED) {
            failureCount.incrementAndGet();
            lastFailureTime = Instant.now();

            if (failureCount.get() >= failureThreshold) {
                state = State.OPEN;
                log.warn("CircuitBreaker state changed to OPEN");
            }
        }
    }
}
