package com.qyl.framework.bean;

/**
 * @Author: qyl
 * @Date: 2020/11/22 16:18
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 视图返回对象
 * 用于封装Controller方法的视图返回结果
 */
public class View {

    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
