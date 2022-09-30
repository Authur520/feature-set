package com.authur.template.autoconfiguration.bootstrap;

import com.authur.template.autoconfiguration.service.TestService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/9/8 18:49
 */
@ComponentScan("com.example.authur.server.template.demo.autoconfiguration.service")
public class ServiceBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ServiceBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        TestService testService = context.getBean("testService", TestService.class);
        System.out.println("TestService Bean: " + testService);
        context.close();
    }

}
