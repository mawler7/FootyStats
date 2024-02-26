package com.footystars.foot8.api.service.requester;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResponseHandler {

    private final ObjectMapper objectMapper;

    public <T> T getObjectFromJson(@NotNull Response response, Class<T> responseType) throws IOException {
        var jsonData = Objects.requireNonNull(response.body()).string();
        return objectMapper.readValue(jsonData, responseType);
    }


}
