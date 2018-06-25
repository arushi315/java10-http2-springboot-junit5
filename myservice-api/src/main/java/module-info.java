module com.nischit.sample.myservice.api {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.webmvc;
    requires com.nischit.sample.myservice.api.support;
    requires spring.web;
    requires com.nischit.sample.myservice.domain;
    requires spring.hateoas;
    requires spring.beans;
    requires com.nischit.sample.myservice.persistence;
    requires com.nischit.sample.myservice.localization;

    opens com.nischit.sample.myservice.services;
    opens com.nischit.sample.myservice.services.impl;
    opens com.nischit.sample.myservice.api.web.controller;
}