package com.xiaoming.day21.aop;

import com.xiaoming.day21.orm.annotation.ExtInsert;
import com.xiaoming.day21.orm.annotation.ExtParam;
import com.xiaoming.day21.orm.annotation.ExtSelect;
import com.xiaoming.day21.orm.utils.JDBCUtils;
import com.xiaoming.day21.orm.utils.SQLUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用反射动态代理技术 拦截接口方法
 */
public class MyInvocationHandlerMybatis implements InvocationHandler {
    private Object object;

    public MyInvocationHandlerMybatis(Object object){
        this.object = object;
    }

    /**
     * 使用白话文翻译，@ExtInsert 封装过程
     *
     * 1. 判断方法上是否存在@ExtInsert
     * 2. 获取 SQL 语句，获取注解 Insert 语句
     * 3. 获取方法上的参数和 SQL 参数进行匹配
     * 4. 替换参数变为 ？
     * 5. 调用 JDBC 底层代码执行语句
     *
     * @param proxy 生成的代理类对象
     * @param method 被拦截的方法
     * @param args 被拦截的方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("使用反射动态代理技术拦截接口方法开始");
        //1. 判断方法上是否存在@ExtInsert
        ExtInsert extInsert = method.getAnnotation(ExtInsert.class);
        if (extInsert != null) {
            return extInsert(method, args, extInsert);
        }

        //查询思路
        //1.判断方法上查询的SQL语句
        ExtSelect extSelect = method.getAnnotation(ExtSelect.class);
        if (extSelect != null){
            //2. 获取 SQL 语句，获取注解 select 语句
            String selectSql = extSelect.value();
            //3. 获取方法上的参数和 SQL 参数进行匹配
            //定义一个 Map 集合，key：@ExtParam     value：参数值
            ConcurrentHashMap<Object, Object> paramMap = getparamsMap(method, args);
            //存放 SQL 的执行参数--参数绑定过程
            List<String> selectParameter = SQLUtils.sqlSelectParameter(selectSql);
            List<Object> sqlParams = new ArrayList<>();
            for (String paramName : selectParameter) {
                Object paramValue = paramMap.get(paramName);
                sqlParams.add(paramValue);
            }

            String newSql = SQLUtils.parameQuestion(selectSql, selectParameter);
            System.out.println(newSql);
            //5. 调用 JDBC 底层代码执行语句
            ResultSet resultSet = JDBCUtils.query(newSql, sqlParams);

            //6,使用反射机制实例化对象 获取方法返回类型，进行实例化
            //1.使用反射机制获取方法的类型
            //2.判断是否有结果集，如果有结果集，再进行初始化
            //3.使用反射机制，给对象赋值

            //判断是否存在质
            if (!resultSet.next()){
                return null;
            }
            //下标往上移动一位
            resultSet.previous();
            //1.使用反射机制获取方法的类型
            Class<?> returnType = method.getReturnType();
            Object object = returnType.newInstance();
            while (resultSet.next()){
                Field[] fields = returnType.getDeclaredFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object filedValue = resultSet.getObject(fieldName);
                    field.setAccessible(true);
                    field.set(object, filedValue);
                }
                /*for (String paramName : selectParameter) {
                    Object resultValue = resultSet.getObject(paramName);
                    //使用 java 反射机制赋值
                    Field field = returnType.getDeclaredField(paramName);
                    field.setAccessible(true);
                    field.set(object, resultValue);
                }*/
            }
            return object;
        }

        return null;
    }

    private Object extInsert(Method method, Object[] args, ExtInsert extInsert) {
        //2. 获取 SQL 语句，获取注解 Insert 语句
        String insertSql = extInsert.value();
        System.out.println(insertSql);

        //3. 获取方法上的参数和 SQL 参数进行匹配
        //定义一个 Map 集合，key：@ExtParam     value：参数值
        ConcurrentHashMap<Object, Object> paramMap = getparamsMap(method, args);
        //存放 SQL 的执行参数--参数绑定过程
        String[] sqlInsertParameter = SQLUtils.sqlInsertParameter(insertSql);
        List<Object> sqlParams = sqlParams(paramMap, sqlInsertParameter);
        //4. 替换参数变为 ？
        String newSql = SQLUtils.parameQuestion(insertSql, sqlInsertParameter);
        System.out.println(newSql);
        //5. 调用 JDBC 底层代码执行语句
        int insert = JDBCUtils.insert(newSql, false, sqlParams);
        return 1;
    }

    private List<Object> sqlParams(ConcurrentHashMap<Object, Object> paramMap, String[] sqlInsertParameter) {
        List<Object> sqlParams = new ArrayList<>();
        for (String paramName : sqlInsertParameter) {
            Object paramValue = paramMap.get(paramName);
            sqlParams.add(paramValue);
        }
        return sqlParams;
    }


    private ConcurrentHashMap<Object, Object> getparamsMap(Method method, Object[] args) {
        ConcurrentHashMap<Object, Object> paramMap = new ConcurrentHashMap<>();
        //获取方法上的参数
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            ExtParam extParam = parameter.getAnnotation(ExtParam.class);
            if (extParam!=null){
                //参数名称
                String paramName = extParam.value();
                Object paramValue = args[i];
                paramMap.put(paramName, paramValue);
            }
        }
        return paramMap;
    }
}
