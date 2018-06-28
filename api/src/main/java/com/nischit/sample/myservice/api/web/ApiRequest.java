/*
 *  Project GreenBox
 *  (c) 2015-2015 VMware, Inc. All rights reserved.
 *  VMware Confidential.
 */

package com.nischit.sample.myservice.api.web;

import java.io.Serializable;
import java.time.Instant;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.UUID.randomUUID;

public class ApiRequest implements Serializable {

    public static final String API_REQUEST_CONTEXT = "apiRequestContext";

    private final String requestUrl;
    private final String requestMethod;
    private final String requestId;
    private final long requestStartTime;
    private final Locale locale;

    public ApiRequest(final String requestUrl, final String requestMethod, final Locale locale) {
        checkArgument(requestUrl != null, "Request URL can't be null.");
        checkArgument(requestMethod != null, "Request Method can't be null.");
        checkArgument(locale != null, "Request locale can't be null.");
        this.requestUrl = requestUrl;
        this.requestId = randomUUID().toString();
        this.requestMethod = requestMethod;
        requestStartTime = Instant.now().toEpochMilli();
        this.locale = locale;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public long getRequestStartTime() {
        return requestStartTime;
    }

    public Locale getLocale() {
        return locale;
    }
}
