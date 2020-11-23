package com.qyl.framework;

/**
 * @Author: qyl
 * @Date: 2020/11/22 16:39
 */

import com.qyl.framework.helper.BeanHelper;
import com.qyl.framework.helper.ClassHelper;
import com.qyl.framework.helper.ControllerHelper;
import com.qyl.framework.helper.IocHelper;
import com.qyl.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类（实际上是加载 Helper 类中的静态代码块）
 * 其实不写这个入口程序，这些类也会被加载
 * 只是为了加载更加集中
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class,
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
