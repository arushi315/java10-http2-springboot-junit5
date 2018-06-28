package com.nischit.sample.myservice.api.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Locale;

import static com.nischit.sample.myservice.api.web.ApiRequest.API_REQUEST_CONTEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ApiRequestInterceptorTest {

    @InjectMocks
    private ApiRequestInterceptor apiRequestInterceptor;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void preHandleSucceeds() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest.setRequestURI("/myurl");
        mockHttpServletRequest.setMethod("GET");
        assertTrue(apiRequestInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null));
        final ApiRequest apiRequest = (ApiRequest)mockHttpServletRequest.getAttribute(API_REQUEST_CONTEXT);
        assertTrue(apiRequest.getRequestUrl().contains("myurl"));
        assertEquals("GET", apiRequest.getRequestMethod());
        assertNotNull(apiRequest.getRequestId());
    }

    @Test
    public void postHandleSucceeds() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        final ApiRequest apiRequest = new ApiRequest("/myurl", "GET", Locale.ENGLISH);
        mockHttpServletRequest.setAttribute(API_REQUEST_CONTEXT, apiRequest);
        MDC.put("requestId", apiRequest.getRequestId());
        apiRequestInterceptor.postHandle(mockHttpServletRequest, mockHttpServletResponse, null, null);
        assertNull(MDC.getCopyOfContextMap());
    }
}