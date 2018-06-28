module com.nischit.sample.myservice.persistence {
    exports com.nischit.sample.myservice.persistence;
    requires com.nischit.sample.myservice.domain;
    requires spring.data.commons;
    requires spring.context;
    requires spring.beans;
    requires hibernate.jpa;
    requires com.google.common;
    requires spring.core;
    requires spring.data.jpa;
    requires spring.tx;
    requires java.xml.bind;

    opens com.nischit.sample.myservice.persistence.sql;
    opens com.nischit.sample.myservice.persistence.sql.repository;
    opens com.nischit.sample.myservice.persistence.sql.entity;
}