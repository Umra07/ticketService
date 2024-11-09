package com.jfb.tickets.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
public class CustomConfiguration {

    @Bean
    @ConditionalOnProperty(name = "bean.enabled", havingValue = "true")
    public void ThisIsMyFirstConditionalBean() {
        System.out.println("Conditional bean is working!!!!");
    }
}
