package com.qyl.framework.helper;

/**
 * @Author: qyl
 * @Date: 2020/11/20 19:51
 */

import com.qyl.framework.annotation.Controller;
import com.qyl.framework.annotation.Service;
import com.qyl.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 * 借助ClassUtil类来实现ClassHelper助手类
 * ClassHelper助手类在自身被加载的时候通过ConfigHelper助手类获取应用的基础包名
 * 然后通过ClassUtil工具类来获取基础包名下的所有类
 * 储存到CLASS_SET集合中
 */
public final class ClassHelper {
    /**
     * 定义类集合(存放包名下的所有类)
     */
    private static final Set<Class<?>> CLASS_SET;

    /**
     * 静态代码块：只执行一次且首先执行
     * 用来获取基础包名和基础包名下的所有类
     */
    static {
        // 获取基础包名
        String basePackage = ConfigHelper.getAppBasePackage();
        // 获取基础包名下的所有类
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取基础包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 把注解的类加入到与之对应的ClassSet
     */

    /**
     * 获取基础包名下所有的 Service 类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            // 判断CLASS_SET中的哪些类是加了 @Service 注解
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取基础包名下所有的 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            // 判断CLASS_SET中的哪些类是加了 @Service 注解
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取基础包名下所有的 Bean 类（包括Controller、Service）
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getServiceClassSet());
        return classSet;
    }

    /**
     * 获取基础包名下某父类的所有子类，或某接口的所有实现类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            // isAssignableFrom: 指superClass与cls是否相同 或 superClass是否为cls的父类或接口
            // 在这里的if表达式里就限定 superClass必须要为cls的父类或接口 才可以通过
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取基础包名下带有注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
