package com.footystars.foot8.api.service.fetcher;


import com.footystars.foot8.api.service.requester.RequestCreator;
import com.footystars.foot8.api.service.requester.RequestExecutor;
import com.footystars.foot8.api.service.requester.ResponseHandler;
import com.footystars.foot8.exception.DataFetcherException;
import com.footystars.foot8.exception.RequestCreatorException;
import com.footystars.foot8.exception.RequestExecutorException;
import com.footystars.foot8.exception.ResponseHandlerException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.footystars.foot8.utils.LogsNames.API_ERROR;


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