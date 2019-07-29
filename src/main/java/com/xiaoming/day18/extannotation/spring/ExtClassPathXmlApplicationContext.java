package com.xiaoming.day18.extannotation.spring;

import com.xiaoming.day18.utils.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: xiaoming
 * @Date: 11:33 2019/7/29
 * 手写 spring IOC 注解版本
 */
public class ExtClassPathXmlApplicationContext {
    /**
     * 扫包的范围
     */
    private String packageName;

    /**
     * spring bean 的容器
     */
    private ConcurrentHashMap<String, Object> beans = null;

    
    public ExtClassPathXmlApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<String, Object>();
        this.packageName = packageName;
        initBean();
        initEntryField();
    }

    //初始化属性
    private void initEntryField() throws IllegalAccessException {
        //1.遍历所有的 bean 容器对象
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            //2.判断对象上面是否加注解
            Object bean = entry.getValue();
            attrAssign(bean);
        }
    }

    /**
     * 初始化对象
     */
    public void initBean() throws Exception {
        //1.使用 java 反射机制扫包，获取当前包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses(packageName);
        //2.判断类上是否存在注入 bean 的注解
        ConcurrentHashMap<String,Object> classExisAnnotation = findClassExisAnnotation(classes);
        if (classExisAnnotation == null || classExisAnnotation.isEmpty()){
            throw new Exception("该包下没有任何类加上注解");
        }
        //3.使用反射机制初始化类
    }

    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)){
            throw new Exception("beanId 参数不能为空");
        }
        //1.从 spring 容器中获取bean
        Object object = beans.get(beanId);
        if (object == null){
            throw new Exception("class not find");
        }
        return object;
    }

    public Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    /**
     * 判断类上是否存在注入 bean 的注解， 返回使用注解的bean
     * @param classes
     * @return
     */
    public ConcurrentHashMap<String, Object> findClassExisAnnotation(List<Class<?>> classes) throws InstantiationException, IllegalAccessException {
        for (Class<?> classInfo : classes) {
            ExtService annotation = classInfo.getAnnotation(ExtService.class);
            if (annotation != null){
                //获取当前类名
                String className = classInfo.getSimpleName();
                //将当前类名首字母变为小写
                String beanId = toLowerCaseFirstOne(className);
                Object bean = newInstance(classInfo);
                beans.put(beanId, bean);
            }
        }
        return beans;
    }

    /**
     * 首字母小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 依赖注入注解原理
     * @param object
     * @throws IllegalAccessException
     */
    public void attrAssign(Object object) throws IllegalAccessException {
        //1.使用反射机制获取当前类所有的属性
        Class<?> classInfo = object.getClass();
        Field[] fields = classInfo.getDeclaredFields();
        //2.判断当前属性是否存在注解
        for (Field field : fields) {
            //获取属性名称
            String beanId = field.getName();
            Object bean = beans.get(beanId);
            ExtResource extResource = field.getAnnotation(ExtResource.class);
            if (extResource != null){
                //3.默认使用属性名称，查找 bean 容器对象
                field.setAccessible(true); //允许访问私有属性
                //参数1 当前对象  参数2 给属性赋值
                field.set(object, bean);
            }
        }


    }

}
