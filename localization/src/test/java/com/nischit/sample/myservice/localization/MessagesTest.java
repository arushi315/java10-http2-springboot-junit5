package com.nischit.sample.myservice.localization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessagesTest {
    private static final String MESSAGE_KEY = "MESSAGE_KEY";
    private static final String DEFAULT_ERROR_MESSAGE = "DEFAULT_ERROR_MESSAGE";
    private static final String LOCALIZED_ERROR_MESSAGE = "LOCALIZED_ERROR_MESSAGE";
    private static final String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";
    private static final String LOCALIZED_MESSAGE = "LOCALIZED_MESSAGE";

    @Mock
    MessageSource messageSource;

    @Mock
    MessageSource errorMessageSource;

    @InjectMocks
    Messages messages;

    private Locale nonDefaultLocale=Locale.FRENCH;

    @BeforeEach
    public void setup() throws Exception {

    }
    @Test
    public void testDefaultErrorMessage(){
        when(errorMessageSource.getMessage(eq(MESSAGE_KEY), isNull(), eq(Messages.DEFAULT_LOCALE))).thenReturn(DEFAULT_ERROR_MESSAGE);
        String message = messages.getErrorMessage(MESSAGE_KEY,null);
        assert(message.equals(DEFAULT_ERROR_MESSAGE));
    }
    @Test
    public void testLocalizedErrorMessage(){
        when(errorMessageSource.getMessage(eq(MESSAGE_KEY), isNull(), eq(nonDefaultLocale))).thenReturn(LOCALIZED_ERROR_MESSAGE);
        messages.setLocale(nonDefaultLocale);
        String message = messages.getLocalizedErrorMessage(MESSAGE_KEY, null);
        assert(message.equals(LOCALIZED_ERROR_MESSAGE));
    }
}