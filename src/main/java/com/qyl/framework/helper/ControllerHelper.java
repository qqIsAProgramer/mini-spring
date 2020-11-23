package com.qyl.framework.helper;

/**
 * @Author: qyl
 * @Date: 2020/11/21 11:22
 */

import com.qyl.framework.annotation.RequestMapping;
import com.qyl.framework.bean.Handler;
import com.qyl.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * 定义一个 “请求-处理器” 的映射 REQUEST_MAP
 * REQUEST_MAP 相当于Spring MVC里的映射处理器，为请求的URI设置对应的处理器
 *
 * REQUEST_MAP 实现逻辑如下
 * 首先通过 ClassHelper 工具类获取到应用中所有Controller的Class对象
 * 然后遍历Controller及其所有方法，将所有带 @RequestMapping 注解的方法封装为处理器
 * 将 @RequestMapping 注解里的请求路径和请求方法封装成请求对象，然后存入 REQUEST_MAP 中
 */
public final class ControllerHelper {

    /**
     * “请求-处理器” 的映射
     */
    private static final Map<Request, Handler> REQUEST_MAP = new HashMap<>();

    static {
        // 遍历所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                // 暴力反射获取所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                // 遍历方法
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        // 判断是否带 @RequestMapping 注解
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            // 请求方法
                            String requestMethod = requestMapping.method().name();
                            // 请求路径
                            String requestPath = requestMapping.value();

                            // 封装请求和处理器
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            REQUEST_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     * @param requestMethod 请求方法
     * @param requestPath 请求路径
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_MAP.get(request);
    }
}
