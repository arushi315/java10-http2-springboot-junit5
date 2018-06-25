/*
 * Project GreenBox
 * (c) 2015-2018 VMware, Inc. All rights reserved.
 * VMware Confidential.
 */

package com.nischit.sample.myservice.util;

import com.nischit.sample.myservice.localization.LocalizationParamValueException;
import com.nischit.sample.myservice.localization.MsgKey;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
@MsgKey("json.serialization.error")
public class SerializerException extends LocalizationParamValueException {

    public SerializerException(final Throwable throwable) {
        super(throwable);
    }
}
