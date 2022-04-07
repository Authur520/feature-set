package com.authur.li.aop;


import org.junit.After;
import org.junit.Before;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/6 11:00
 */

@EnableAspectJAutoProxy
public class MyAspect {

    public static void main(String[] args) {
        beforeNotify1();
    }

    public static void beforeNotify1() {
        System.out.println("******** @Before我是前置通知MyAspect");
        System.out.println(SpringVersion.getVersion());
        System.out.println(SpringBootVersion.getVersion());
    }

//    @Before("execution(public int com.atguigu.study.spring.aop.CalcServiceImpl.*(..))")
//    public void beforeNotify() {
//        System.out.println("********@Before我是前置通知MyAspect");
//    }
//
//
//    @After("execution(public int com.atguigu.study.spring.aop.CalcserviceImpl.*(..))")
//    public void afterNotify() {
//        System.out.println("********@After我是后置通知");
//    }
//
//    @AfterReturning("execution(public int com.atguigu.study.spring.aop.CalcserviceImpl.*(..))")
//    public void afterReturningNotify() {
//        System.out.println("********@Afterketurning我是返回后通知");
//    }
//
//    @AfterThrowing("execution(public int com.atguigu.study.spring.aop.CalcserviceImpl.(..))")
//    public void afterThrowingNotify() {
//        System.out.println("********@AfterThrowing我是异常通知");
//    }
//
//    @Around("execution(public int com.atguigu.study.spring.aop.CalcserviceImpl.*(..))")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        Object retValue = null;
//        System.out.println("我是环绕通知之前AAA");
//        retValue =proceedingJoinPoint.proceed();
//        System.out.println("我是环绕通知之后BBe");
//    }


}

