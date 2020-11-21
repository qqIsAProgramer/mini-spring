package com.qyl;

import java.lang.reflect.Field;
import java.net.URL;

/**
 * @Author: qyl
 * @Date: 2020/11/20 16:35
 */
public class MyTest {



    public static void main(String[] args) throws Exception {
//        MyTest t = new MyTest();
//        System.out.println(MyTest.class.getResource(""));
//        System.out.println(t.getClass().getResource(""));

//        String packageName = "D:/java.class.name";
//        System.out.println(packageName.replace(".", "/"));

//        URL url = Thread.currentThread().getContextClassLoader().getResource("");
//        System.out.println(url.getProtocol());
//        System.out.println(url.getPath());
//        System.out.println(url.getPath().replaceAll("%20", " "));

//        String jarEntryName = "java/class/test.class";
//        if (jarEntryName.endsWith(".class")) {
//            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
//            System.out.println(className);
//        }


        Test2 t = new Test2();
        Class<? extends Test2> cls = t.getClass();
        Field field = cls.getDeclaredField("mInt");
////        field.setAccessible(false);
//
//        String fieldName = field.getName();
//        System.out.println(fieldName);
//        field.set(t, 100);
//        int value = field.getInt(t);
//        System.out.println(value);
        Test2.setField(t, field, 1);

    }
}

class Test2 {
    public static void setField(Object obj, Field field, Object value) {
        try {
            // 除去私有权限
//            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
//            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }

    private int mInt;
}
