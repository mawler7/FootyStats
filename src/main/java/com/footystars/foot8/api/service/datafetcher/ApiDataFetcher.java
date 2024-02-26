package com.footystars.foot8.api.service.datafetcher;


import com.footystars.foot8.api.service.requester.RequestCreator;
import com.footystars.foot8.api.service.requester.RequestExecutor;
import com.footystars.foot8.api.service.requester.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class ApiDataFetcher {

    private final RequestExecutor executor;
    private final RequestCreator creator;
    private final ResponseHandler handler;

    public <T> T fetch(String pathSegments, Map<String, String> params, Class<T> responseType) throws IOException {
        var request = creator.createRequest(pathSegments, params);
        var response = executor.executeRequest(request);
        return handler.getObjectFromJson(response, responseType);
    }

}

