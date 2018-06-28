package com.nischit.sample.myservice.services.impl;

import com.nischit.sample.myservice.localization.LocalizationParamValueException;
import com.nischit.sample.myservice.localization.MsgKey;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
@MsgKey("team.not.found")
public class TeamNotFoundException extends LocalizationParamValueException {

    public TeamNotFoundException(Object...args) {
        super(args);
    }
}
