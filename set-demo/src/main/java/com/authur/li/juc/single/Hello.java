package com.authur.li.juc.single;


/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/6 16:17
 */

public enum Hello {

    PLATINUM;

    private Hello getInstance(){
        return PLATINUM;
    }

}
