package com.xiaoming.day19.ext.springmvc.servlet;

import com.xiaoming.day19.ext.springmvc.extannotation.ExtController;
import com.xiaoming.day19.ext.springmvc.extannotation.ExtRequestMapping;
import com.xiaoming.day19.utils.ClassUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtDispatcherServlet extends HttpServlet {

    /**
     * springmvc 容器对象 key：类名id value：对象
     */
    private ConcurrentHashMap<String, Object> springmvcBeans = new ConcurrentHashMap<>();

    /**
     * springmvc 容器对象 key：请求地址 value：对象
     */
    private ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<>();

    /**
     * springmvc 容器对象 key：请求地址 value：方法名称
     */
    private ConcurrentHashMap<String, Object> urlMethods = new ConcurrentHashMap<>();

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
            if (extController!=null){
                //默认类名小写
                String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
                //对象
                Object object = ClassUtil.newInstance(classInfo);
                springmvcBeans.put(beanId, object);
            }
        }
    }

    public void handlerMapping(){
        //1.遍历 springmvc bean 容器 判断类上是否有 url 映射注解
        for (Map.Entry<String, Object> mvcBean : springmvcBeans.entrySet()) {
            //2.遍历所有方法上是否有url注解
           //获取 bean 对象
            Object object = mvcBean.getValue();
            //3.判断类上是否有 url 映射注解
            Class<?> classInfo = object.getClass();
            ExtRequestMapping extRequestMapping = classInfo.getDeclaredAnnotation(ExtRequestMapping.class);
            String baseUrl = null;
            if (extRequestMapping != null){
                //获取类上的 url 地址
                baseUrl = extRequestMapping.value();
            }
            //4.判断方法上是否有url映射地址
            Method[] declaredMethods = classInfo.getDeclaredMethods();
            for (Method method : declaredMethods) {
                //判断方法上是否有url映射注释
                ExtRequestMapping methodExtDeclaredAnnotation = method.getDeclaredAnnotation(ExtRequestMapping.class);
                if (methodExtDeclaredAnnotation != null){
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
