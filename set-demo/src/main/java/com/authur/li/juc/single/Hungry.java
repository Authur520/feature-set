package com.authur.li.juc.single;

public class Hungry {
    private Hungry(){

    }
    private final static Hungry HUNGRY = new Hungry();

    private static Hungry getInstance(){
        return HUNGRY;
    }

}
