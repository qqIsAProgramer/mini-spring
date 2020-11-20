package com.qyl;

import java.net.URL;

/**
 * @Author: qyl
 * @Date: 2020/11/20 16:35
 */
public class MyTest {

    public static void main(String[] args) {
//        MyTest t = new MyTest();
//        System.out.println(MyTest.class.getResource(""));
//        System.out.println(t.getClass().getResource(""));

//        String packageName = "D:/java.class.name";
//        System.out.println(packageName.replace(".", "/"));

        URL url = Thread.currentThread().getContextClassLoader().getResource("");
//        System.out.println(url.getProtocol());
        System.out.println(url.getPath());
        System.out.println(url.getPath().replaceAll("%20", " "));

//        String jarEntryName = "java/class/test.class";
//        if (jarEntryName.endsWith(".class")) {
//            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
//            System.out.println(className);
//        }

    }
}
