package com.authur.li.juc.callable;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author authur
 * @description:
 */
@Data
@Component
public class User {
    private String name;
    private int age;

    public User() {
        this.name = "Car";
        this.age = 12;
    }
}
