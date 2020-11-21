package com.qyl.framework.helper;

/**
 * @Author: qyl
 * @Date: 2020/11/21 0:24
 */

import com.qyl.framework.annotation.Autowired;
import com.qyl.framework.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * 依赖注入助手类
 * 实现IOC功能
 * 遍历Bean容器中的所有Bean
 * 为所有带 @Autowired 注解的属性注入实例
 * 这个实例从Bean容器中获取
 */
public final class IocHelper {

    static {
        // 遍历容器所有的Bean
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // Bean的Class类
                Class<?> beanClass = beanEntry.getKey();
                // Bean的实例化
                Object beanInstance = beanEntry.getValue();
                // 暴力反射获取Class的属性
                Field[] beanFields = beanClass.getDeclaredFields();
                // 遍历Bean的属性
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        // 判断属性是否带有 @Autowired 注解
                        if (beanField.isAnnotationPresent(Autowired.class)) {
                            // 属性类型
                            Class<?> beanFieldClass = beanField.getType();
                            // 如果beanFieldClass是接口则获取接口对应的实现类
                            beanFieldClass = findImplementClass(beanFieldClass);
                            // 获取Class类对应的实例
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                // 设置BeanInstance成员变量的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取接口对应的实现类
     * @param interfaceClass
     * @return
     */
    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        // 接口对应的所有实现类
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(interfaceClass);
        if (CollectionUtils.isNotEmpty(classSetBySuper)) {
            // 获取第一个实现类
            implementClass = classSetBySuper.iterator().next();
        }
        return implementClass;
    }
}
