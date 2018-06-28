package com.nischit.sample.myservice.api.web;

import com.nischit.sample.myservice.localization.Messages;
import com.nischit.sample.myservice.services.impl.TeamNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.util.Locale;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@ExtendWith(MockitoExtension.class)
public class ApiExceptionResolverTest {

    @Mock
    private Messages mockMessages;

    private ApiExceptionResolver apiExceptionResolver;

    @BeforeEach
    public void setUp() {
        apiExceptionResolver = new ApiExceptionResolver(mockMessages);
    }

    @Test
    @DisplayName("When application exception is thrown")
    public void handleApplicationExceptionSucceeds() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        when(mockMessages.getLocalizedErrorMessage(any(String.class), any(Locale.class), any())).thenReturn("my error msg");

        final ResponseEntity<String> responseEntity = apiExceptionResolver.handleApplicationException(mockHttpServletRequest, mockHttpServletResponse, new TeamNotFoundException());
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(NOT_FOUND, mockHttpServletRequest.getAttribute(ERROR_STATUS_CODE));
        assertEquals(TeamNotFoundException.class, mockHttpServletRequest.getAttribute(ERROR_EXCEPTION_TYPE));
    }

    @Test
    @DisplayName("When bad request exception is thrown")
    public void handleBadRequestExceptionSucceeds() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        when(mockMessages.getLocalizedErrorMessage(any(String.class), any(Locale.class), any())).thenReturn("my error msg");

        final ResponseEntity<String> responseEntity = apiExceptionResolver.handleBadRequestException(mockHttpServletRequest, mockHttpServletResponse, new IllegalArgumentException());
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(BAD_REQUEST, mockHttpServletRequest.getAttribute(ERROR_STATUS_CODE));
        assertEquals(IllegalArgumentException.class, mockHttpServletRequest.getAttribute(ERROR_EXCEPTION_TYPE));
    }

    @Test
    @DisplayName("When media type not supported exception is thrown")
    public void handleHttpMediaTypeNotSupportedExceptionSucceeds() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        when(mockMessages.getLocalizedErrorMessage(any(String.class), any(Locale.class), any())).thenReturn("my error msg");

        final ResponseEntity<String> responseEntity = apiExceptionResolver.handleHttpMediaTypeNotSupportedException(mockHttpServletRequest, mockHttpServletResponse, new HttpMediaTypeNotSupportedException("application/json"));
        assertEquals(UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(UNSUPPORTED_MEDIA_TYPE, mockHttpServletRequest.getAttribute(ERROR_STATUS_CODE));
        assertEquals(HttpMediaTypeNotSupportedException.class, mockHttpServletRequest.getAttribute(ERROR_EXCEPTION_TYPE));
    }

    @Test
    @DisplayName("When media type not acceptable exception is thrown")
    public void handleHttpMediaTypeNotAcceptableExceptionSucceeds() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        when(mockMessages.getLocalizedErrorMessage(any(String.class), any(Locale.class), any())).thenReturn("my error msg");

        final ResponseEntity<String> responseEntity = apiExceptionResolver.handleHttpMediaTypeNotAcceptableException(mockHttpServletRequest, mockHttpServletResponse, new HttpMediaTypeNotAcceptableException("application/json"));
        assertEquals(NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(NOT_ACCEPTABLE, mockHttpServletRequest.getAttribute(ERROR_STATUS_CODE));
        assertEquals(HttpMediaTypeNotAcceptableException.class, mockHttpServletRequest.getAttribute(ERROR_EXCEPTION_TYPE));
    }

    @Test
    @DisplayName("When any unhandled error is thrown")
    public void handleAnyGenericException() {
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        when(mockMessages.getLocalizedErrorMessage(any(String.class), any(Locale.class), any())).thenReturn("my error msg");

        final ResponseEntity<String> responseEntity = apiExceptionResolver.handleAnyGenericException(mockHttpServletRequest, mockHttpServletResponse, new NullPointerException());
        assertEquals(INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(INTERNAL_SERVER_ERROR, mockHttpServletRequest.getAttribute(ERROR_STATUS_CODE));
        assertEquals(NullPointerException.class, mockHttpServletRequest.getAttribute(ERROR_EXCEPTION_TYPE));
    }
}