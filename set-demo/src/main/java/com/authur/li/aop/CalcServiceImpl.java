package com.authur.li.aop;

import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/6 10:59
 */
@Service
public class CalcServiceImpl implements CalcService{
    @Override
    public int div(int x, int y) {

        int result = x/y;

        System.out.println("    =======>CalcServiceImpl被调用了，计算结果："+result);
        return result;
    }
}
