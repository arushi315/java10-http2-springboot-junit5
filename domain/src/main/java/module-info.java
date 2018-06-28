module com.nischit.sample.myservice.domain {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.google.common;
    requires spring.core;
    exports com.nischit.sample.myservice.domain.teams;
}