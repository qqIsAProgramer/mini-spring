package com.qyl.framework;

/**
 * @Author: qyl
 * @Date: 2020/11/22 23:40
 */

import com.alibaba.fastjson.JSON;
import com.qyl.framework.bean.Data;
import com.qyl.framework.bean.Handler;
import com.qyl.framework.bean.Param;
import com.qyl.framework.bean.View;
import com.qyl.framework.helper.BeanHelper;
import com.qyl.framework.helper.ConfigHelper;
import com.qyl.framework.helper.ControllerHelper;
import com.qyl.framework.helper.RequestHelper;
import com.qyl.framework.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 请求转发器
 * 该Servlet将会在Web容器启动时加载
 * 当 DispatcherServlet 实例化时，首先执行 init() 方法
 * 这时会调用 HelperLoader.init() 方法来加载相关的Helper类，并注册处理相应资源的Servlet
 *
 * 对于每一次客户端请求都会执行service()方法，这时会首先将请求方法和请求路径封装成Request对象
 * 然后映射处理器（REQUEST_MAP）中获取处理器
 * 然后从客户端请求中获取到Param参数对象，执行处理方法
 * 最后判断处理器方法的返回值
 * 如果为View类型，则跳转到jsp页面
 * 如果为Data类型，则返回json数据
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public final class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 初始化相关Helper类
        HelperLoader.init();

        // 获取ServletContext对象，用于注册Servlet
        ServletContext servletContext = servletConfig.getServletContext();

        // 注册处理jsp和静态资源的servlet
        registerServlet(servletContext);
    }

    /**
     * jspServlet和defaultServlet都是由web容器创建
     * @param servletContext
     */
    private void registerServlet(ServletContext servletContext) {
        // 动态注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 动态注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        // 网站头像
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toUpperCase();
        String requestPath = req.getPathInfo();

        // 这里根据Tomcat的配置路径有两种情况，一种是 "localhost:8080/userList", 另一种是 "localhost:8080/context地址/userList"
        String[] splits = requestPath.split("/");
        if (splits.length > 2) {
            requestPath = "/" + splits[2];
        }

        // 根据请求获取处理器（这里类似与SpringMVC中的映射处理器）
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            // 初始化参数
            Param param = RequestHelper.createParam(req);

            // 调用与请求对应的方法（这里类似于SpringMVC中的处理器适配器）
            Object result;
            Method controllerMethod = handler.getControllerMethod();
            if (param == null || param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, controllerMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, controllerMethod, param);
            }

            // 跳转页面或返回json数据（这里类似于SpringMVC中的视图解析器）
            if (result instanceof View) {
                handlerViewResult((View) result, req, resp);
            } else if (result instanceof Data) {
                handlerDataResult((Data) result, resp);
            }
        }
    }

    /**
     * 跳转页面
     * @param view
     * @param request
     * @param response
     */
    private void handlerViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtils.isNotEmpty(path)) {
            // 重定向
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                // 请求转发
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    /**
     * 返回JSON数据
     * @param data 需要转换成JSON的数据
     * @param response
     * @throws IOException
     */
    private void handlerDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = JSON.toJSON(model).toString();
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
