package com.qyl.framework.bean;

/**
 * @Author: qyl
 * @Date: 2020/11/21 11:13
 */

import java.lang.reflect.Method;

/**
 * 封装 Controller 信息
 * 封装 Controller 的Class对象和Method方法
 */
public class Handler {

    /**
     * Controller 类
     */
    private Class<?> controllerClass;

    /**
     * Controller方法
     */
    private Method controllerMethod;

    public Handler(Class<?> controllerClass, Method controllerMethod) {
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
