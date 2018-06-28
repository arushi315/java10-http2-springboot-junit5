/*
 * Project GreenBox
 * (c) 2015-2018 VMware, Inc. All rights reserved.
 * VMware Confidential.
 */

package com.nischit.sample.myservice.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.nischit.sample.myservice.support.ApiUrls.ERROR_CONFIG_URI;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;

@Controller
public class ApiErrorController {

    @Autowired
    private ApiExceptionResolver apiExceptionResolver;

    @RequestMapping(value = ERROR_CONFIG_URI)
    public ResponseEntity<String> sendError(final HttpServletRequest request, final HttpServletResponse response) {
        return apiExceptionResolver.handleAnyGenericException(request, response, (Exception) request.getAttribute(ERROR_EXCEPTION));
    }

}
