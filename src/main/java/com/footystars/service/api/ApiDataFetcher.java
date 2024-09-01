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


@Service
@RequiredArgsConstructor
public class ApiDataFetcher {

    private final RequestExecutor executor;
    private final RequestCreator creator;
    private final ResponseHandler handler;

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