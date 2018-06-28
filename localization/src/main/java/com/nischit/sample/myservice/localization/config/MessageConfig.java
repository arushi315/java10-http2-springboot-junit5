package com.nischit.sample.myservice.localization.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    /**
     * Bean for Non Error Messages
     * Looks for Files Messages_{Locale} in the classpath.
     * Example:Messages_en_US.properties.
     */
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:Messages");
        return messageSource;
    }

    /**
     * Bean for  Error Messages
     * Looks for Files ErrorMessages_{Locale} in the classpath.
     * Example:ErrorMessages_en_US.properties.
     */
    @Bean(name = "errorMessageSource")
    public MessageSource errorMessageSource() {
        ReloadableResourceBundleMessageSource errorMessageSource = new ReloadableResourceBundleMessageSource();
        errorMessageSource.setBasename("classpath:ErrorMessages");
        return errorMessageSource;
    }
}
