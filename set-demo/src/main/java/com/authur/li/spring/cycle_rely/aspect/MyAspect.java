package com.authur.li.spring.cycle_rely.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/7/14 10:27
 */
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(public * com.authur.li.spring.cycle_rely.component.BeanA.getBeanB())")
    public void pointcut() {

    }
    @Before("pointcut()")
    public void onBefore(JoinPoint joinPoint) {
        System.out.println("onBefore：" + joinPoint.getSignature().getName() + "方法开始执行");
    }
}
