package com.footystars.foot8.api.service.fetcher;

import static org.mockito.Mockito.mock;

class ApiDataFetcherTest {

//    @Mock
//    private RequestExecutor executor;
//
//    @Mock
//    private RequestCreator creator;
//
//    @Mock
//    private ResponseHandler handler;
//
//    @InjectMocks
//    private ApiDataFetcher apiDataFetcher;
//
//
//    @Test
//    public void fetch_SuccessfulRequest_ReturnsData() throws IOException {
//        // Arrange
//        String pathSegments = "example/path";
//        Map<String, String> params = new HashMap<>();
//        Class<T> responseType = YourResponseClass.class;
//        Request request = mock(Request.class);
//        Response response = mock(Response.class);
//        T expectedData = mock(YourResponseClass.class);
//
//        when(creator.createRequest(pathSegments, params)).thenReturn(request);
//        when(executor.executeRequest(request)).thenReturn(response);
//        when(handler.getObjectFromJson(response, responseType)).thenReturn(expectedData);
//
//        // Act
//        T actualData = apiDataFetcher.fetch(pathSegments, params, responseType);
//
//        // Assert
//        assertEquals(expectedData, actualData);
//    }
//
//    @Test(expected = DataFetcherException.class)
//    public void fetch_FailedRequest_ThrowsDataFetcherException() throws IOException {
//        // Arrange
//        String pathSegments = "example/path";
//        Map<String, String> params = new HashMap<>();
//        Class<T> responseType = YourResponseClass.class;
//        Request request = mock(Request.class);
//
//        when(creator.createRequest(pathSegments, params)).thenReturn(request);
//        when(executor.executeRequest(request)).thenThrow(new IOException());
//
//        // Act
//        apiDataFetcher.fetch(pathSegments, params, responseType);
//
//        // Assert
//        // Expected exception
//    }
}