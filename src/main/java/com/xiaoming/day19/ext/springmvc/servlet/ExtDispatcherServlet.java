package com.xiaoming.day19.ext.springmvc.servlet;

import com.xiaoming.day19.ext.springmvc.extannotation.ExtController;
import com.xiaoming.day19.ext.springmvc.extannotation.ExtRequestMapping;
import com.xiaoming.day19.utils.ClassUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtDispatcherServlet extends HttpServlet {

    /**
     * springmvc 容器对象 key：类名id value：对象
     */
    private ConcurrentHashMap<String, Object> springmvcBeans = new ConcurrentHashMap<String, Object>();

    /**
     * springmvc 容器对象 key：请求地址 value：对象
     */
    private ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<String, Object>();

    /**
     * springmvc 容器对象 key：请求地址 value：方法名称
     */
    private ConcurrentHashMap<String, String> urlMethods = new ConcurrentHashMap<String, String>();

    @Override
    public void init() throws ServletException {
        //1.获取当前包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses("com.xiaoming.day19.controller");
        //2.将扫包范围所有的类，注入到 springmvc 容器里面，存放在 Map 集合中，key：默认类名小写，value 对象
        try {
            findClassMVCAnnotation(classes);
        } catch (Exception e) {
            // TODO
        }
        //3.将 url 映射和方法进行关联
        handlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的url
        String requestURI = req.getRequestURI();
        //2.从 map 集合获取控制器对象
        Object object = urlBeans.get(requestURI);
        if (object == null){
            resp.getWriter().println("404 not found url");
        }
        //3.根据url找到对应的方法
        String methodName = urlMethods.get(requestURI);
        if (methodName == null){
            resp.getWriter().println("not found method");
        }
        //4.使用反射机制执行方法，返回执行结果
        String resultPage = (String) methodInvoke(object, methodName);
//        resp.getWriter().println(resultPage);
        //5.调用视图转换器渲染给页面显示
        extResourceViewResoler(resultPage, req, resp);
    }

    /**
     * 调用视图转换器渲染给页面显示
     * @param resultPage
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void extResourceViewResoler(String resultPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prefix = "/";
        String suffix = ".jsp";
        req.getRequestDispatcher(prefix + resultPage + suffix).forward(req,resp);
    }

    /**
     * 使用反射机制执行方法
     * @param object 目标对象
     * @param methodName 方法名称
     * @return 执行结果
     */
    private Object methodInvoke(Object object, String methodName){
        Class<?> classInfo = object.getClass();
        try {
            Method method = classInfo.getMethod(methodName);
            Object result = method.invoke(object);
            return result;
        } catch (Exception e) {
            return null;
        } 
    }

    /**
     * 将扫包范围所有的类，注入到 springmvc 容器里面，存放在 Map 集合中，key：默认类名小写，value 对象
     *
     * @param classes
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void findClassMVCAnnotation(List<Class<?>> classes) throws InstantiationException, IllegalAccessException {
        for (Class<?> classInfo : classes) {
            //判断类上是否有注解
            ExtController extController = classInfo.getAnnotation(ExtController.class);
            if (extController != null) {
                //默认类名小写
                String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
                //对象
                Object object = ClassUtil.newInstance(classInfo);
                springmvcBeans.put(beanId, object);
            }
        }
    }

    /**
     * 将 url 映射和方法进行关联
     */
    public void handlerMapping() {
        //1.遍历 springmvc bean 容器 判断类上是否有 url 映射注解
        for (Map.Entry<String, Object> mvcBean : springmvcBeans.entrySet()) {
            //2.遍历所有方法上是否有url注解
            //获取 bean 对象
            Object object = mvcBean.getValue();
            //3.判断类上是否有 url 映射注解
            Class<?> classInfo = object.getClass();
            ExtRequestMapping extRequestMapping = classInfo.getAnnotation(ExtRequestMapping.class);
            String baseUrl = "";
            if (extRequestMapping != null) {
                //获取类上的 url 地址
                baseUrl = extRequestMapping.value();
            }
            //4.判断方法上是否有url映射地址
            Method[] declaredMethods = classInfo.getDeclaredMethods();
            for (Method method : declaredMethods) {
                //判断方法上是否有url映射注释
                ExtRequestMapping methodExtDeclaredAnnotation = method.getAnnotation(ExtRequestMapping.class);
                if (methodExtDeclaredAnnotation != null) {
                    String methodUrl = baseUrl + methodExtDeclaredAnnotation.value();
                    //springmvc 容器对象 key：请求地址 value：对象
                    urlBeans.put(methodUrl, object);
                    //springmvc 容器对象 key：请求地址 value：方法名称
                    urlMethods.put(methodUrl, method.getName());
                }
            }
        }
    }
}
