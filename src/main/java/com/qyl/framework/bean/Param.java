package com.qyl.framework.bean;

/**
 * @Author: qyl
 * @Date: 2020/11/22 16:04
 */

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * 请求参数对象
 * 用于封装请求参数对象Controller方法的参数
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param() {
    }

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public boolean isEmpty() {
        return MapUtils.isEmpty(paramMap);
    }
}
