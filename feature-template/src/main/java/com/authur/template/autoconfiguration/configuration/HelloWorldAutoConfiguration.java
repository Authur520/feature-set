package com.authur.template.autoconfiguration.configuration;


import com.authur.template.autoconfiguration.annotation.EnableHelloWorld;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/9/13 16:54
 */
@Configuration
@EnableHelloWorld
@ConditionalOnProperty(value = "helloworld", havingValue = "true")
public class HelloWorldAutoConfiguration {
}
