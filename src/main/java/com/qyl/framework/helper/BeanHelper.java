package com.qyl.framework.helper;

/**
 * @Author: qyl
 * @Date: 2020/11/20 23:55
 */

import com.qyl.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 * BeanHelper 在类加载时会创建一个Bean容器 BEAN_MAP
 * 然后获取应用中所有bean的Class对象
 * 再通过反射创建bean实例存储到 BEAN_MAP 中
 */
public final class BeanHelper {
    /**
     * BEAN_MAP相当于一个Bean容器，拥有项目所有的Bean实例
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    /**
     * 首先执行将所有的Bean放入BEAN_MAP中
     */
    static {
        // 获取所有的Bean
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        // 将Bean实例化，并放入Bean容器中
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取 Bean 容器
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     * <T>标识为一个泛型方法
     */
    public static <T> T getBean(Class<?> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置 Bean 实例
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }
}
