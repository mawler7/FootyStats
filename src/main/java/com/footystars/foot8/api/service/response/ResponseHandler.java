package com.footystars.foot8.api.service.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.foot8.api.service.limiter.RequestsLimiterService;
import com.footystars.foot8.exception.ClientErrorException;
import com.footystars.foot8.exception.ServerErrorException;
import com.footystars.foot8.exception.UnexpectedStatusCodeException;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.footystars.foot8.utils.ApiRequestParams.REQUEST_LIMIT;
import static com.footystars.foot8.utils.ApiRequestParams.REQUEST_REMAINING;

@Service
@RequiredArgsConstructor
public class ResponseHandler {

    private final ObjectMapper objectMapper;

    private final RequestsLimiterService requestLimitReader;

    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);

    public <T> T handleResponse(@NotNull Response response, Class<T> responseType) throws IOException {
        int statusCode = response.code();
        if (statusCode >= 200 && statusCode < 300) {
            return parseResponse(response, responseType);
        } else if (statusCode >= 400 && statusCode < 500) {
            log.warn("Client error: {}", statusCode);
            throw new ClientErrorException("Client error: " + statusCode);
        } else if (statusCode >= 500 && statusCode < 600) {
            log.error("Server error: {}", statusCode);
            throw new ServerErrorException("Server error: " + statusCode);
        } else {
            log.error("Unexpected status code: {}", statusCode);
            throw new UnexpectedStatusCodeException("Unexpected status code: " + statusCode);
        }
    }

    private <T> T parseResponse(@NotNull Response response, Class<T> responseType) throws IOException {
        var jsonData = response.body().string();
        var limit = Integer.parseInt(Objects.requireNonNull(response.headers().get(REQUEST_LIMIT)));
        var remaining = Integer.parseInt(Objects.requireNonNull(response.headers().get(REQUEST_REMAINING)));
        requestLimitReader.update(limit,remaining);
        return objectMapper.readValue(jsonData, responseType);
    }
}