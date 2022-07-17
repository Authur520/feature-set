package com.authur.li.spring.cycle_rely.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/7/14 10:28
 */
@Component
public class BeanB {

    public void My(){
        System.out.println("BeanB success!!!!!!!!!!!");
    }

    @Autowired
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
