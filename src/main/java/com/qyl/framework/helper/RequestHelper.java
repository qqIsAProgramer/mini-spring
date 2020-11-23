package com.qyl.framework.helper;

/**
 * @Author: qyl
 * @Date: 2020/11/22 16:26
 */

import com.qyl.framework.bean.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求助手类
 * 前端控制器接收到HTTP请求后
 * 从HTTP中获取请求参数
 * 如何封装到Param对象中
 */
public final class RequestHelper {

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        // 如果没有参数
        if (!parameterNames.hasMoreElements()) {
            return null;
        }

        // get和post参数都能获取到
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String fieldValue = request.getParameter(fieldName);
            paramMap.put(fieldName, fieldValue);
        }
        return new Param(paramMap);
    }
}
