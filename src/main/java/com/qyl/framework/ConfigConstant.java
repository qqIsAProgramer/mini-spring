package com.qyl.framework;

/**
 * @Author: qyl
 * @Date: 2020/11/19 23:59
 */

/**
 * 提供配置相关项常量
 * 类似与SpringBoot中的application.properties
 */
public interface ConfigConstant {

    // 配置文件名称
    String CONFIG_FILE = "spring.properties";

    // 数据库相关
    String JDBC_DRIVER = "spring.framework.jdbc.driver";
    String JDBC_URL = "spring.framework.jdbc.url";
    String JDBC_USERNAME = "spring.framework.jdbc.username";
    String JDBC_PASSWORD = "spring.framework.jdbc.password";

    // 相关文件地址
    // java源码地址
    String APP_BASE_PACKAGE = "spring.framework.app.base_package";
    // jsp页面路径
    String APP_JSP_PATH = "spring.framework.app.jsp_path";
    // 静态资源路径
    String APP_ASSET_PATH = "spring.framework.app.asset_path";
}
