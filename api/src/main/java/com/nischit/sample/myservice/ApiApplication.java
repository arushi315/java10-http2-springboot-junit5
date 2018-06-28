package com.nischit.sample.myservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import static com.nischit.sample.myservice.support.ApiUrls.API_CONTEXT_PATTERN;
import static com.nischit.sample.myservice.support.ApiUrls.ERROR_CONFIG_URI;
import static org.springframework.boot.Banner.Mode.OFF;
import static org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME;

@SpringBootApplication
public class ApiApplication {

    public static final String API_SERVLET_NAME = "API_SERVLET";
    public static final String API_SERVLET_REG_BEAN = "API_SERVLET_REG_BEAN";

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApiApplication.class);
        application.setBannerMode(OFF);
        application.run(args);
    }

    @Bean(name = API_SERVLET_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean(name = API_SERVLET_REG_BEAN)
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet(), API_CONTEXT_PATTERN, ERROR_CONFIG_URI);
        registration.setName(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return registration;
    }
}
