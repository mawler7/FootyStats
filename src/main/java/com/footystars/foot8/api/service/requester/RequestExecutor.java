package com.footystars.foot8.api.service.requester;

import com.footystars.foot8.exception.RequestExecutorException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RequestExecutor {

    private final OkHttpClient httpClient;

    @NotNull
    public Response executeRequest(@NotNull Request request) {
        try {
            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RequestExecutorException(e, "Failed to execute request");
        }
    }

}
