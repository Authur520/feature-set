package com.authur.li.jvm;

import com.authur.li.juc.demo;
import sun.misc.Launcher;

import java.net.URL;

/**
 * @author authur
 * @description:
 */
public class ClassLoader {
    public static void main(String[] args) {

        //启动类加载器
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println("===>" + url.toExternalForm());
        }

        //扩展类加载器
        ClassLoader.class.getClassLoader().getParent();
        //应用类加载器
        ClassLoader.class.getClassLoader();
    }
}
