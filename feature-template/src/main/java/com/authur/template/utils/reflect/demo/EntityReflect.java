package com.authur.template.utils.reflect.demo;


import java.lang.reflect.Field;
import java.text.DecimalFormat;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/9/28 15:51
 */
public class EntityReflect {

    //判断对象不为空所占比
    public String entity(Object dealerBaseInfoEntity){
        int a = 0;
        Class clazz = dealerBaseInfoEntity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(dealerBaseInfoEntity) != null && !field.get(dealerBaseInfoEntity).equals("")) {
                    a++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        String integrity = df.format(a * 100.00 / fields.length) + "%";
        a = 0;
        return integrity;
    }



}


