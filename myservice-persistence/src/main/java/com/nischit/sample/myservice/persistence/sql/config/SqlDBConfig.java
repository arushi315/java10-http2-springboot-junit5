package com.nischit.sample.myservice.persistence.sql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.nischit.sample.myservice.persistence.sql")
public class SqlDBConfig {
}
