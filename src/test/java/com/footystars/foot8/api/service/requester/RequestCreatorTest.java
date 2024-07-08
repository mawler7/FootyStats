package com.footystars.foot8.api.service.requester;

import com.footystars.foot8.config.RapidApiConfig;
import com.footystars.foot8.utils.PathSegment;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

 class RequestCreatorTest {

    @Mock
    private RapidApiConfig rapidApiConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testCreateRequest() {
        RequestCreator requestCreator = new RequestCreator(rapidApiConfig);
        when(rapidApiConfig.getApiHost()).thenReturn(PathSegment.API_HOST);
        when(rapidApiConfig.getApiKey()).thenReturn("API_KEY");
        Map<String, String> queryParams = Map.of("param1", "value1", "param2", "value2");
        Request request = requestCreator.createRequest("/path", queryParams);
        assertNotNull(request);
        assertEquals("GET", request.method());
        assertEquals("https://api-football-v1.p.rapidapi.com//path?param1=value1&param2=value2", request.url().toString());
        assertEquals("API_KEY", request.header(PathSegment.RAPID_API_KEY_HEADER));
    }
}