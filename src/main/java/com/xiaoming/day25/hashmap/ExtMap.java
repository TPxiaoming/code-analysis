package com.xiaoming.day25.hashmap;

/**
 * 手写 Map 接口
 */
public interface ExtMap<K, V> {

    /**
     * 向集合中插入数据
     * @param k
     * @param v
     * @return
     */
    public V put(K k, V v);

    /**
     * 根据 k 从 map 集合中查询元素
     * @param k
     * @return
     */
    public V get(K k);

    /**
     * 获取集合元素的个数
     * @return
     */
    public int size();

    /**
     * Entry 的作用 == Node 节点
     * @param <K>
     * @param <V>
     */
    interface Entry<K, V>{
        K getKey();

        V getValue();

        V setValue(V value);
    }
}
