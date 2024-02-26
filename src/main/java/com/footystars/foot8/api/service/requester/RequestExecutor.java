package com.footystars.foot8.api.service.requester;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Response executeRequest(@NotNull Request request) throws IOException {
        return httpClient.newCall(request).execute();

    }

}
