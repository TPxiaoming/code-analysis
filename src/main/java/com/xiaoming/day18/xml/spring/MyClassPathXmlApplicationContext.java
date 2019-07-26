package com.xiaoming.day18.xml.spring;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: xiaoming
 * @Date: 16:25 2019/7/26
 * 自定义 spring 容器框架 xml 方式实现
 */
public class MyClassPathXmlApplicationContext {

    private String xmlPath;

    public MyClassPathXmlApplicationContext(String xmlPath){
        this.xmlPath = xmlPath;
    }
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)){
            throw new Exception("beanId 不能为空");
        }
        //1.解析xml文件，获取所有bean节点信息
        List<Element> elements = ReadXml();
        if (elements == null || elements.isEmpty()){
            throw new Exception("配置文件中没有配置bean信息");
        }

        //2.使用方法参数bean id查找配置文件中 bean 节点的 id 信息是否一致， 获取 class信息地址
        String xmlClassPath = findByElementClass(elements, beanId);

        if (StringUtils.isEmpty(xmlClassPath)){
            throw new Exception("该bean对象没有配置class地址");
        }

        //3.使用反射机制初始化
        Object instance = newInstance(xmlClassPath);
        return instance;
    }

    /**
     * 使用方法参数bean id查找配置文件中 bean 节点的 id 信息是否一致， 获取 class信息地址
     * @param elements
     * @param beanId
     * @return
     */
    public String findByElementClass( List<Element> elements, String beanId){
        for (Element element : elements) {
            String xmlBeanId = element.attributeValue("id");
            if (StringUtils.isEmpty(xmlBeanId)){
                continue;
            }
            if (xmlBeanId.equals(beanId)){
                String xmlClassPath = element.attributeValue("class");
               return xmlClassPath;
            }
        }
        return null;
    }

    /**
     * 初始化悐
     * @param className
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> classInfo = Class.forName(className);
        Object instance = classInfo.newInstance();
        return instance;
    }


    /**
     * 解析 xml 文件
     * @return 所有叶子节点
     * @throws DocumentException
     */
    public List<Element> ReadXml() throws DocumentException {
        //1.解析 xml 文件信息
        SAXReader saxReader = new SAXReader();
        //读取xml文件
        Document document = saxReader.read(getResourceAsStream(xmlPath));
        //2.读取根节点
        Element rootElement = document.getRootElement();

        //3.读取根节点的所有叶子节点
        List<Element> elements = rootElement.elements();
        return elements;
    }


    public InputStream getResourceAsStream(String xmlPath){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        return resourceAsStream;
    }
}
