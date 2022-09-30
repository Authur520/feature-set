package com.authur.template.autoconfiguration.bootstrap;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/9/13 16:45
 */
@EnableAutoConfiguration
public class EnableAutoConfigurationBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(EnableAutoConfigurationBootstrap.class).web(WebApplicationType.NONE).run(args);
        String hello = context.getBean("hello", String.class);
        System.out.println("hello bean:::"+hello);
        context.close();
    }
}
