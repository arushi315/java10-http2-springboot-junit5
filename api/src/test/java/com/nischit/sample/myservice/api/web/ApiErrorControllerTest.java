package com.nischit.sample.myservice.api.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApiErrorControllerTest {

    @Mock
    private ApiExceptionResolver mockApiExceptionResolver;

    @InjectMocks
    private ApiErrorController apiErrorController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendErrorSucceeds() {

        apiErrorController.sendError(mock(HttpServletRequest.class), mock(HttpServletResponse.class));
        verify(mockApiExceptionResolver).handleAnyGenericException(any(HttpServletRequest.class), any(HttpServletResponse.class), any());
    }
}