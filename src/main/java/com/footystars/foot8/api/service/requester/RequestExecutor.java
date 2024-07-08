package com.footystars.foot8.api.service.requester;

import com.footystars.foot8.exception.RequestExecutorException;
import com.footystars.foot8.utils.LogsNames;
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

import static com.footystars.foot8.utils.LogsNames.RETRY_AFTER;
import static com.footystars.foot8.utils.LogsNames.TOO_MANY_REQUESTS;

@Service
public class RequestExecutor {

    private final OkHttpClient httpClient;
    private final RequestsLimiterService limitReader;
    private final Logger logger = LoggerFactory.getLogger(RequestExecutor.class);
    private final AtomicInteger requestCount = new AtomicInteger(0);

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;
    private static final long LIMIT = 75000;
    private static final int MAX_REQUESTS_PER_MINUTE = 450;

    public RequestExecutor(OkHttpClient httpClient, RequestsLimiterService limitReader) {
        this.httpClient = httpClient;
        this.limitReader = limitReader;
        var scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> requestCount.set(0), 0, 1, TimeUnit.MINUTES);
    }

    public Response executeRequest(Request request) {
        long remaining = limitReader.getRemaining();

        if (remaining >= LIMIT) {
            throw new RequestExecutorException(LogsNames.API_CALL_LIMIT_EXCEEDED);
        }

        logger.info(LogsNames.API_CALLS_TODAY, remaining);
        return attemptRequestWithRetries(request);
    }

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


    private void sleepBeforeRetry() {
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @NotNull
    private Response executeWithRetry(@NotNull Request request) throws IOException {
        logger.debug(LogsNames.EXECUTING_REQUEST, request.url());
        Response response = httpClient.newCall(request).execute();

        if (response.code() == 429) {
            handleTooManyRequests(response);
        }

        return response;
    }

    private void handleTooManyRequests(@NotNull Response response) throws IOException {
        String retryAfterHeader = response.header(RETRY_AFTER);
        int retryAfter = retryAfterHeader != null ? Integer.parseInt(retryAfterHeader) : 1;

        try {
            logger.warn(LogsNames.RECEIVED_429_RETRYING, retryAfter);
            Thread.sleep(retryAfter * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        throw new IOException(LogsNames.RECEIVED_429_TOO_MANY_REQUESTS);
    }

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
                        wait(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new RequestExecutorException(ex, LogsNames.THREAD_INTERRUPTED_RATE_LIMITING);
                    }
                }
            }
        }
    }

}
