package com.nischit.sample.myservice.localization;

/**
 * Base exception class which will be extended by other Exception classes
 * Allows passing of variable args which can be utilized for localizing messages by Messages.class
 */
public class LocalizationParamValueException extends RuntimeException {
    private final Object[]  args;

    public LocalizationParamValueException(Object... args) {
        this.args = args;
    }

    public LocalizationParamValueException(final Throwable throwable, Object... args) {
        super(throwable);
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }
}
