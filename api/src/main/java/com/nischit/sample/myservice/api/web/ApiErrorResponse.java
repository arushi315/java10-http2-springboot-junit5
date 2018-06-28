/*
 * Project GreenBox
 * (c) 2015-2018 VMware, Inc. All rights reserved.
 * VMware Confidential.
 */
package com.nischit.sample.myservice.api.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@JsonInclude(NON_NULL)
public class ApiErrorResponse implements Serializable {
    private String code;

    private String message;

    private Map<String, Object> addnlErrorInfo;

    public ApiErrorResponse(String code, String message) {
        checkArgument(hasText(code));
        checkArgument(hasText(message));
        this.code = code;
        this.message = message;
    }

    public ApiErrorResponse(String code, String message, Map<String, Object> addnlErrorInfo) {
        this(code, message);
        checkArgument(nonNull(addnlErrorInfo));
        this.addnlErrorInfo = addnlErrorInfo;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getAddnlErrorInfo() {
        return addnlErrorInfo;
    }
}