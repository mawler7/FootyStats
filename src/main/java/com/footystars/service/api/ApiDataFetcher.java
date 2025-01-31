package com.footystars.service.api;

import com.footystars.service.requester.RequestCreator;
import com.footystars.service.requester.RequestExecutor;
import com.footystars.service.requester.ResponseHandler;
import com.footystars.exception.DataFetcherException;
import com.footystars.exception.RequestCreatorException;
import com.footystars.exception.RequestExecutorException;
import com.footystars.exception.ResponseHandlerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.footystars.utils.LogsNames.API_ERROR;

/**
 * Service responsible for fetching data from external APIs.
 */
@Service
@RequiredArgsConstructor
public class ApiDataFetcher {

    private final RequestExecutor executor;
    private final RequestCreator creator;
    private final ResponseHandler handler;

    /**
     * Fetches data from an external API by creating a request, executing it, and handling the response.
     *
     * @param pathSegments The API path segments.
     * @param params       The query parameters to be included in the request.
     * @param responseType The expected response type.
     * @param <T>          The generic type parameter for the response object.
     * @return The parsed response object of type {@code T}.
     * @throws IOException          If an error occurs during response parsing.
     * @throws DataFetcherException If an error occurs during request creation, execution, or response handling.
     */
    public <T> T fetch(String pathSegments, Map<String, String> params, Class<T> responseType) throws IOException {
        try {
            var request = creator.createRequest(pathSegments, params);
            var response = executor.executeRequest(request);
            return handler.handleResponse(response, responseType);
        } catch (RequestCreatorException | RequestExecutorException | ResponseHandlerException e) {
            throw new DataFetcherException(API_ERROR, e);
        }
    }
}
