package com.nischit.sample.myservice.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.Instant;

import static com.nischit.sample.myservice.api.web.ApiRequest.API_REQUEST_CONTEXT;

public class ApiRequestInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ApiRequest apiRequest = new ApiRequest(request.getRequestURL().toString(), request.getMethod(), request.getLocale());
        request.setAttribute(API_REQUEST_CONTEXT, apiRequest);
        MDC.put("requestId", apiRequest.getRequestId());
        LOGGER.info("Request recieved for uri {} and http method {}", apiRequest.getRequestUrl(), apiRequest.getRequestMethod());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                              @Nullable ModelAndView modelAndView) {
        final long endRequestEndTime = Instant.now().toEpochMilli();
        ApiRequest apiRequest = (ApiRequest)request.getAttribute(API_REQUEST_CONTEXT);
        LOGGER.info("Request completed for uri {} and http method {} in {}ms", apiRequest.getRequestUrl(), apiRequest.getRequestMethod(), endRequestEndTime - apiRequest.getRequestStartTime());
        MDC.clear();
    }
}
