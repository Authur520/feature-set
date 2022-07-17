package com.authur.li.spring.cycle_rely;

import com.authur.li.spring.cycle_rely.component.BeanA;
import com.authur.li.spring.cycle_rely.component.BeanB;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @Description: Spring
 * @Author: jibing.Li
 * @Date: 2022/7/14 10:20
 */

@SpringBootApplication
public class SpringCycleApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringCycleApplication.class, args);
        BeanA beanA = context.getBean(BeanA.class);
        BeanB beanB = context.getBean(BeanB.class);
        BeanB beanBInBeanA = beanA.getBeanB();
        BeanA beanAInBeanB = beanB.getBeanA();
        System.out.println("BeanA是否为代理对象：" + AopUtils.isAopProxy(beanA));
        System.out.println("BeanB是否为代理对象：" + AopUtils.isAopProxy(beanB));
        System.out.println("beanAInBeanB是否为代理对象：" + AopUtils.isAopProxy(beanAInBeanB));
        System.out.println(beanB == beanBInBeanA);
        System.out.println(beanA == beanAInBeanB);
    }
}

