module com.nischit.sample.myservice.api {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.webmvc;
    requires spring.web;
    requires com.nischit.sample.myservice.domain;
    requires spring.hateoas;
    requires spring.beans;
    requires com.nischit.sample.myservice.persistence;
    requires com.nischit.sample.myservice.localization;
    requires com.fasterxml.jackson.annotation;
    requires com.google.common;
    requires spring.core;
    requires javax.servlet.api;
    requires slf4j.api;
    requires com.nischit.sample.myservice.util;
    requires com.nischit.sample.myservice.support;
    requires com.fasterxml.jackson.databind;

    opens com.nischit.sample.myservice.services;
    opens com.nischit.sample.myservice.services.impl;
    opens com.nischit.sample.myservice.api.web.controller;
}