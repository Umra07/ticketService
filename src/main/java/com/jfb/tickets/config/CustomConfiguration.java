package com.jfb.tickets.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

  @Bean
  @ConditionalOnProperty(name = "bean.enabled", havingValue = "true")
  public ThisIsMyFirstConditionalBean thisIsMyFirstConditionalBean() {
    System.out.println("Conditional bean is working!!!!");
    return new ThisIsMyFirstConditionalBean();
  }
}
