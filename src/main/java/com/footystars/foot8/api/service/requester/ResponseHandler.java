package com.footystars.foot8.api.service.requester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.foot8.exception.ResponseHandlerException;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResponseHandler {

    private final ObjectMapper objectMapper;

    public <T> T getObjectFromJson(@NotNull Response response, @NotNull Class<T> responseType)   {
        try {
            var jsonData = Objects.requireNonNull(response.body()).string();
            return objectMapper.readValue(jsonData, responseType);
        } catch (IOException e) {
            throw new ResponseHandlerException("Could not read JSON from response");
        }
    }

}
