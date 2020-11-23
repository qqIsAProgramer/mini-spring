package com.qyl.framework.bean;

/**
 * @Author: qyl
 * @Date: 2020/11/22 16:14
 */

/**
 * 返回数据对象
 * 用于封装Controller方法的JSON返回结果
 */
public class Data {

    /**
     * 数据模型
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
