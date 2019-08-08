package com.xiaoming.day24.hashmap;


import java.util.ArrayList;
import java.util.List;

public class ExtArrayListHashMap<Key, Value> {

    /**
     * hashmap 容器
     */
    private List<Entry<Key, Value>> tables = new ArrayList<>();

    public void put(Key key, Value value) {
        Entry oldEntry = getEntry(key);
        if (oldEntry != null){
            //已经存在
            oldEntry.value = value;
        }else{
            Entry<Key, Value> entry = new Entry<>(key, value);
            //调用put的时候，将该hash存储到arraylist中
            tables.add(entry);
        }

    }

    public Value get(Key key) {
        Entry<Key, Value> entry = getEntry(key);
        return entry == null ? null : entry.value;
    }

    public Entry<Key, Value> getEntry(Key key) {
        //从头查询到尾
        for (Entry<Key, Value> entry : tables) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * hash存储
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
        ExtArrayListHashMap<String, String> map = new ExtArrayListHashMap<>();
        map.put("1", "a");
        map.put("1", "b");
        map.put("2", "c");
        System.out.println(map.get("1"));
    }
}
