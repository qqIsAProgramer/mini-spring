package com.qyl.framework.bean;

/**
 * @Author: qyl
 * @Date: 2020/11/21 10:33
 */

import java.util.Objects;

/**
 * 封装请求信息
 * 请求类中的方法与路径对应 @RequestMapping 注解里的方法和路径
 */
public class Request {

    /**
     * 请求路径
     */
    private String requestMethod;

    /**
     * 请求方法
     */
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        Request request = (Request) o;
        return request.getRequestPath().equals(this.requestPath) && request.getRequestMethod().equals(this.requestMethod);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + requestMethod.hashCode();
        result = 31 * result + requestPath.hashCode();
        return result;
    }
}
