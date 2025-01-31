package com.footystars.service.requester;

import com.footystars.exception.RequestCreatorException;
import com.footystars.config.api.ApiConfig;
import com.footystars.utils.PathSegment;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.footystars.utils.ParameterName.COULD_NOT_CREATE_REQUEST;

/**
 * Service responsible for creating HTTP requests for external API calls.
 */
@Service
@RequiredArgsConstructor
public class RequestCreator {

    private final ApiConfig rapidApiConfig;

    /**
     * Constructs an HTTP URL with the specified path segments and query parameters.
     *
     * @param pathSegments The path segments for the URL.
     * @param queryParams  A map containing query parameters to be added to the URL.
     * @return A fully constructed {@link HttpUrl} object.
     */
    @NotNull
    private static HttpUrl getUrl(@NotNull String pathSegments, @NotNull Map<String, String> queryParams) {
        var urlBuilder = new HttpUrl.Builder()
                .scheme(PathSegment.SCHEME)
                .host(PathSegment.API_HOST)
                .addPathSegments(pathSegments);
        if (!queryParams.isEmpty()) {
            queryParams.forEach(urlBuilder::addQueryParameter);
        }
        return urlBuilder.build();
    }

    /**
     * Creates an HTTP GET request with the specified path segments and query parameters.
     *
     * @param pathSegments The path segments for the request URL.
     * @param queryParams  A map containing query parameters to be included in the request.
     * @return A fully constructed {@link Request} object.
     * @throws RequestCreatorException If the request cannot be created.
     */
    public Request createRequest(@NotNull String pathSegments, @NotNull Map<String, String> queryParams) {
        try {
            var url = getUrl(pathSegments, queryParams);
            return new Request.Builder()
                    .url(url)
                    .addHeader(PathSegment.RAPID_API_HOST_HEADER, rapidApiConfig.getApiHost())
                    .addHeader(PathSegment.RAPID_API_KEY_HEADER, rapidApiConfig.getApiKey())
                    .get()
                    .build();
        } catch (Exception e) {
            throw new RequestCreatorException(COULD_NOT_CREATE_REQUEST);
        }
    }
}
