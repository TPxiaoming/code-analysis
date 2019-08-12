package com.xiaoming.day25;

import com.xiaoming.day25.hashmap.ExtHashMap;

import java.util.HashMap;

public class Test001 {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "A");
        ExtHashMap<String, String> extHashMap = new ExtHashMap<>();
        extHashMap.put("1号", "1号");// 0
        extHashMap.put("2号", "1号");// 1
        extHashMap.put("3号", "1号");// 2
        extHashMap.put("4号", "1号");// 3
        extHashMap.put("6号", "1号");// 4
        extHashMap.put("7号", "1号");

        extHashMap.put("22号", "1号");
        extHashMap.put("26号", "1号");
        extHashMap.put("27号", "1号");
        extHashMap.put("28号", "1号");
        extHashMap.put("66号", "66");
        extHashMap.put("30号", "1号");
        System.out.println("扩容前数据....");
        extHashMap.print();
        /*extHashMap.put("6号", "123");// 4
        System.out.println("修改数据后....");
        extHashMap.print();
        System.out.println(extHashMap.get("6号"));*/
        System.out.println("扩容后数据....");
        extHashMap.put("31号", "1号");
        extHashMap.put("66号", "123466666");
        extHashMap.print();
        // 修改3号之后
        System.out.println(extHashMap.get("66号"));
        // System.out.println("扩容之前获取数据:" + extHashMap.get("1号"));
        // extHashMap.print();
        // System.out.println();
        // // extHashMap.put(14 + "号", 14 + "号");
        // // extHashMap.put(1 + "号", "4444号");
        // System.out.println("扩容之后获取数据:" + extHashMap.get("1号"));
        // extHashMap.print();

    }
}
