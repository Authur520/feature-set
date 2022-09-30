package com.authur.template.autoconfiguration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/9/8 19:00
 */
@Configuration
public class HelloWorldConfiguration {

    @Bean
    public String hello(){
        return "hello world";
    }
}
