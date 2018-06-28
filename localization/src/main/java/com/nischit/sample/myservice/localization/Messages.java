package com.nischit.sample.myservice.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class for providing locale specific messages and error messages
 * The message sources are initialized in WebConfig class
 */
@Component
public class Messages {
    public static final Locale DEFAULT_LOCALE = Locale.US;
    public static final String SERVER_ERROR_KEY = "unmapped.message";
    public static final String BAD_REQUEST_ERROR = "bad.payload";
    public static final String UNSUPPORTED_MEDIA_TYPE_ERROR = "media.type.unsupported";
    public static final String UNSUPPORTED_ACCEPT_HEADER_ERROR = "accept.header.unsupported";
    private static final String MSG_KEY_NOT_FOUND = "Message Key Not Found";
    private static ThreadLocal<Locale> _tllocale = new ThreadLocal<>();


    @Autowired
    @Qualifier("errorMessageSource")
    private MessageSource errorMessageSource;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    /**
     * Api for getting error message in specific locale
     * Checks if the error message source contains given key in the specific locale
     * If not found, checks it in default locale
     * If not found, returns MSG_KEY_NOT_FOUND
     */
    public String getLocalizedErrorMessage(String msgKey, Object... args){
        return getLocalizedMessage(errorMessageSource, msgKey, args, getLocale());
    }

    /**
     * Api for getting error message in default locale
     */
    public String getErrorMessage(String msgKey, Object... args) {
        return getLocalizedMessage(errorMessageSource, msgKey, args, DEFAULT_LOCALE);
    }
    /**
     * Api for getting  message in specific locale
     * Checks if the message source contains given key in the specific locale
     * If not found, checks it in default locale
     * If not found, returns MSG_KEY_NOT_FOUND
     */
    public String getLocalizedMessage(String msgKey, Object... args){
        return getLocalizedMessage(messageSource, msgKey, args, getLocale());

    }

    /**
     * Api for getting  message in specific locale
     * Checks if the message source contains given key in the specific locale
     * If not found, checks it in default locale
     * If not found, returns MSG_KEY_NOT_FOUND
     */
    public String getLocalizedErrorMessage(String msgKey, Locale locale, Object... args){
        return getLocalizedMessage(errorMessageSource, msgKey, args, locale);

    }

    /**
     * Api for getting  message in default locale
     */
    public String getMessage(String msgKey, Object... args){
        return getLocalizedMessage(messageSource, msgKey, args, DEFAULT_LOCALE);
    }

    /**
     * Sets the locale in thread locale variable
     */
    public static void setLocale(Locale locale) {
        _tllocale.set(locale);
    }
    public static Locale getLocale() {
        Locale locale = _tllocale.get();
        return (locale == null) ? DEFAULT_LOCALE: locale ;
    }

    /**
     * Checks if a message source contains given key in the specific locale
     * If not found, checks it in default locale
     * If not found, returns MSG_KEY_NOT_FOUND
     */
    private String getLocalizedMessage(MessageSource messageSource, String msgKey, Object[] args, Locale locale){ //NOPMD
        String localizedMessage = null;
        try{
            localizedMessage = messageSource.getMessage(msgKey, args, locale);
        }
        catch (NoSuchMessageException e) {
            if(!locale.equals(DEFAULT_LOCALE)){
                try{
                    localizedMessage = messageSource.getMessage(msgKey, args, DEFAULT_LOCALE);
                }
                catch (NoSuchMessageException ex) {
                    localizedMessage = MSG_KEY_NOT_FOUND;
                }
            }
        }
        return localizedMessage;
    }

}
