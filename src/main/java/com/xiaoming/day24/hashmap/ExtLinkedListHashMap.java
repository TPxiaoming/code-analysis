package com.xiaoming.day24.hashmap;

import java.util.LinkedList;

/**
 * 基于LinkedList实现HashMap
 */
public class ExtLinkedListHashMap<Key, Value> {

    //Map 存放 Entry 对象
    LinkedList<Entry>[] tables = new LinkedList[16];

    /**
     * 两个对象做比较的时候，如果 hashCode 相同，对象的值是否一定相同？不一定相同
     * 两个对象做比较的时候，如果 equals 比较相同，对象的值是否一定相同？相同
     * 如果 hashCode 相同情况下，会发生的值的覆盖，使用链表存储解决 hash 冲突
     *
     * 新增方法
     * 1、获取该下标元素，是否有 LinkedList
     * 2.如果 hashCode 相同，对象存放在同一个集合里
     * @param key
     * @param value
     */
    public void put(Key key, Value value) {
        Entry<Key, Value> newEntry = new Entry<>(key, value);
        int hashCode = key.hashCode();
        //hash算法：hash取模。获取余数
        int hash = hashCode % tables.length;
        //获取该下标元素，是否有 LinkedList
        LinkedList<Entry> entryLinkedList = tables[hash];
        if (entryLinkedList == null){
            //没有 hash 冲突
            entryLinkedList = new LinkedList<>();
            entryLinkedList.add(newEntry);
            tables[hash] = entryLinkedList;
        } else {
            //发生 hash 冲突问题
            for (Entry entry : entryLinkedList) {
                if (entry.key.equals(key)){
                    //equals ,hashCode 一定相同说明：是同一个对象
                    entry.value = value;
                } else {
                    //hashcode 相同，值不一定相同
                    entryLinkedList.add(newEntry);
                }
            }
        }
    }

    /**
     * 直接使用 hash 值定位在数组哪个位置
     * @param key
     * @return
     */
    public Object get(Key key){
        int hashCode = key.hashCode();
        //hash算法：hash取模。获取余数
        int hash = hashCode % tables.length;
        LinkedList<Entry> linkedList = tables[hash];
        for (Entry entry : linkedList) {
            if (entry.key.equals(key)){
                return entry.value;
            }
        }
        return null;
    }

    /**
     * hash存储对象
     *
     * @param <Key>
     * @param <Value>
     */
    class Entry<Key, Value> {

        Key key;
        Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        ExtLinkedListHashMap<String, String> list = new ExtLinkedListHashMap<>();
        list.put("a","a");
        list.put("b","b");
        list.put("b","c");
        System.out.println(list.get("b"));
    }
}
