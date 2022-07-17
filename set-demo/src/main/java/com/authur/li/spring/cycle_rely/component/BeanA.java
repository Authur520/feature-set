package com.authur.li.spring.cycle_rely.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/7/14 10:28
 */
@Component
public class BeanA {

    public void My(){
        System.out.println("BeanA success!!!!!!!!!!!");
    }

    @Autowired
    private BeanB beanB;

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}
