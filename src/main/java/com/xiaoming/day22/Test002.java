package com.xiaoming.day22;

import com.xiaoming.day22.arraylist.ExtArrayList;

public class Test002 {
    public static void main(String[] args) {
        ExtArrayList extArrayList = new ExtArrayList();
        extArrayList.add("a");
        extArrayList.add("b");
        extArrayList.add("c");
        extArrayList.add("d");
        extArrayList.add("e");
        extArrayList.remove(0);
        extArrayList.remove("e");
        for (int i = 0; i < extArrayList.getSize(); i++) {
            System.out.println(extArrayList.get(i));
        }
    }
}
