package com.authur.template.autoconfiguration.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/9/8 18:43
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface FirstLevelService {
    String value() default "";
}
