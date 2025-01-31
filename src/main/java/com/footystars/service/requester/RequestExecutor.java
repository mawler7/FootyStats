package com.footystars.service.requester;

import com.footystars.exception.RequestExecutorException;
import com.footystars.utils.LogsNames;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.footystars.utils.LogsNames.RETRY_AFTER;
import static com.footystars.utils.LogsNames.TOO_MANY_REQUESTS;

/**
 * Service responsible for executing HTTP requests with built-in rate limiting and retry mechanisms.
 */
@Service
public class RequestExecutor {

    private final OkHttpClient httpClient;
    private final RequestsLimiterService limitReader;
    private final Logger logger = LoggerFactory.getLogger(RequestExecutor.class);
    private final AtomicInteger requestCount = new AtomicInteger(0);

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;
    private static final Integer LIMIT = 75000;
    private static final int MAX_REQUESTS_PER_MINUTE = 450;

    /**
     * Constructor initializes the rate limiter to reset every minute.
     *
     * @param httpClient  The OkHttpClient instance for executing requests.
     * @param limitReader The service responsible for tracking API rate limits.
     */
    public RequestExecutor(OkHttpClient httpClient, RequestsLimiterService limitReader) {
        this.httpClient = httpClient;
        this.limitReader = limitReader;
        var scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> requestCount.set(0), 0, 1, TimeUnit.MINUTES);
    }

    /**
     * Executes an HTTP request while ensuring API rate limits are respected.
     *
     * @param request The HTTP request to be executed.
     * @return The HTTP response.
     * @throws RequestExecutorException If the API call limit is exceeded or execution fails.
     */
    public Response executeRequest(Request request) {
        var remaining = limitReader.getRemaining();
        if (remaining == null) {
            limitReader.update(LIMIT, 100);
        }
        if (remaining != null && remaining <= 10) {
            throw new RequestExecutorException(LogsNames.API_CALL_LIMIT_EXCEEDED);
        }

        return attemptRequestWithRetries(request);
    }

    /**
     * Attempts to execute a request with retries in case of failures.
     *
     * @param request The HTTP request to be executed.
     * @return The HTTP response.
     * @throws RequestExecutorException If the maximum number of retries is reached.
     */
    @NotNull
    private Response attemptRequestWithRetries(Request request) {
        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            try {
                throttleRequests();
                return executeWithRetry(request);
            } catch (IOException e) {
                if (!handleIOException(e)) {
                    throw new RequestExecutorException(e, LogsNames.FAILED_EXECUTE_IO_EXCEPTION);
                }
                retryCount++;
                sleepBeforeRetry();
            }
        }

        throw new RequestExecutorException(LogsNames.FAILED_EXECUTE_MAX_RETRIES);
    }

    /**
     * Pauses execution before retrying a failed request.
     */
    private void sleepBeforeRetry() {
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Executes an HTTP request and handles rate limit responses (HTTP 429).
     *
     * @param request The HTTP request.
     * @return The HTTP response.
     * @throws IOException If an I/O error occurs during execution.
     */
    @NotNull
    private Response executeWithRetry(@NotNull Request request) throws IOException {
        logger.debug(LogsNames.EXECUTING_REQUEST, request.url());
        Response response = httpClient.newCall(request).execute();

        if (response.code() == 429) {
            handleTooManyRequests(response);
        }
        return response;
    }

    /**
     * Handles HTTP 429 (Too Many Requests) responses by waiting for the required retry period.
     *
     * @param response The HTTP response containing the retry information.
     */
    private void handleTooManyRequests(@NotNull Response response) {
        String retryAfterHeader = response.header(RETRY_AFTER);
        int retryAfter = retryAfterHeader != null ? Integer.parseInt(retryAfterHeader) : 1;

        try {
            logger.warn(LogsNames.RECEIVED_429_RETRYING, retryAfter);
            Thread.sleep(retryAfter * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.error(LogsNames.RECEIVED_429_TOO_MANY_REQUESTS);
    }

    /**
     * Handles different types of {@link IOException} during request execution.
     *
     * @param e The IOException encountered.
     * @return {@code true} if the exception should trigger a retry, otherwise {@code false}.
     */
    private boolean handleIOException(IOException e) {
        if (e instanceof SocketTimeoutException) {
            logger.warn(LogsNames.TIMEOUT_RETRYING, e);
            return true;
        } else if (e.getMessage().contains(TOO_MANY_REQUESTS)) {
            logger.warn(LogsNames.RECEIVED_429_TOO_MANY_REQUESTS_RETRYING, e);
            return true;
        } else {
            logger.error(LogsNames.FAILED_EXECUTE_REQUEST, e);
            return false;
        }
    }

    /**
     * Ensures that the number of requests per minute does not exceed the allowed limit.
     * If the limit is reached, the method waits before proceeding.
     */
    private void throttleRequests() {
        while (true) {
            int currentCount = requestCount.get();
            if (currentCount < MAX_REQUESTS_PER_MINUTE) {
                if (requestCount.compareAndSet(currentCount, currentCount + 1)) {
                    break;
                }
            } else {
                synchronized (this) {
                    try {
                        wait(10000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new RequestExecutorException(ex, LogsNames.THREAD_INTERRUPTED_RATE_LIMITING);
                    }
                }
            }
        }
    }
}
