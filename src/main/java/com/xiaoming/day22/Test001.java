package com.xiaoming.day22;

import com.xiaoming.day22.arraylist.ExtArrayList;

import java.util.ArrayList;

public class Test001 {
    public static void main(String[] args) {
        //原来本身 elementData 容量大小
        int oldCapacity = 1;
        //新数据容量
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        //如果初始容量为1的时候，那么扩容容量为多少？

        /*System.out.println(10 + (10 >> 1));
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);*/
        ExtArrayList list = new ExtArrayList(2);
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.get(3));

        //反射机制 不能够获取泛型类型
    }
}
