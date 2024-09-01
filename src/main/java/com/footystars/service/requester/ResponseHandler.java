package com.footystars.service.requester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.exception.ClientErrorException;
import com.footystars.exception.ServerErrorException;
import com.footystars.exception.UnexpectedStatusCodeException;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.footystars.utils.ApiRequestParams.REQUEST_LIMIT;
import static com.footystars.utils.ApiRequestParams.REQUEST_REMAINING;
import static com.footystars.utils.ParameterName.CLIENT_ERROR;
import static com.footystars.utils.ParameterName.SERVER_ERROR;
import static com.footystars.utils.ParameterName.UNEXPECTED_STATUS_CODE;

@Service
@RequiredArgsConstructor
public class ResponseHandler {

    private final ObjectMapper objectMapper;

    private final RequestsLimiterService requestLimitReader;


    public <T> T handleResponse(@NotNull Response response, Class<T> responseType) throws IOException {
        int statusCode = response.code();
        if (statusCode >= 200 && statusCode < 300) {
            return parseResponse(response, responseType);
        } else if (statusCode >= 400 && statusCode < 500) {
            throw new ClientErrorException(CLIENT_ERROR + statusCode);
        } else if (statusCode >= 500 && statusCode < 600) {
            throw new ServerErrorException(SERVER_ERROR + statusCode);
        } else {
            throw new UnexpectedStatusCodeException(UNEXPECTED_STATUS_CODE + statusCode);
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