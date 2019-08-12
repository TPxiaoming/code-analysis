package com.xiaoming.day25.hashmap;

/**
 * 手写hashMap
 * 1.定义节点
 * 2.定义table 存放HashMap 数组元素 默认是没有初始化容器 懒加载
 * 3.定义实际用到 table 存储容量大小
 * 4.定义负载因子 0.75 扩容的时候才会用到  负载因子越小，他的 hash 冲突越少
 * 5.定义 HashMap 默认初始大小 16
 *
 * @param <K>
 * @param <V>
 */
public class ExtHashMap<K, V> implements ExtMap<K, V> {

    /**
     * 定义table 存放HashMap 数组元素 默认是没有初始化容器 懒加载
     */
    private Node<K, V>[] table = null;
    /**
     * 实际用到 table 存储容量大小
     */
    int size;

    /**
     * 负载因子 0.75 扩容的时候才会用到  负载因子越小，他的 hash 冲突越少
     */
    static  float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * HashMap 默认初始大小 16
     */
    static  int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * 添加元素
     * 1.判断table 是否为空？为空，进行初始化
     * 2.判断数组是否需要扩容
     * 3.计算 hash 值指定下标位置
     * 4.获取指定下标位置的节点 node
     * 5.判断是否发生 hash 冲突
     * 没有发生，创建第一个节点，next 为空
     * 发生，遍历链表上的 node，6.判断 key equals 是否相同
     * 相同 修改值
     * 不相同，往单链表首部添加节点 next 为当前 node
     * <p>
     * hashMap 的 key 为 null， 插入到 0 个位置
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        //判断table 是否为空？为空，进行初始化
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        //判断数组是否需要扩容  hashMap 中是从什么时候开始扩容
        //实际存储大小 = 负载因子 * 初始容量 = DEFAULT_LOAD_FACTOR 0.75 * DEFAULT_INITIAL_CAPACITY 16 = 12
        //如果 size >= 12 的时候就需要开始扩容数组，扩容数组大小是之前的两倍
        if(size >= DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY){
            //需要开始对 table 进行数组扩容
            resize();
        }


        //计算 hash 值指定下标位置
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table[index];
        if (node == null) {
            //没有发生hash冲突
            node = new Node<K, V>(key, value, null);
            size++;
        } else {
            //已经发生 hash 冲突
            Node<K, V> newNode = node;
            while (newNode != null) {
                // == 用来判断值类型 比如 a == 95
                if (newNode.getKey().equals(key) || node.getKey() == key) {
                    // hashcode 和 equals 相同，存放的是同一个对象   修改值
                    V oldValue = newNode.setValue(value);
                    return oldValue;
                } else {
                    //往单链表首部添加节点， hashCode 取模余数相同 index 存放在链表或者 hashCode 相同但是对象不同
                    if (newNode.next == null) {
                        //说明遍历到最后一个 node，添加 node
                        node = new Node<K, V>(key, value, node);
                        size++;
                    }
                }
                newNode = newNode.next;
            }

        }
        table[index] = node;
        return node.getValue();
    }

    /**
     * 测试方法，打印所有的链表的元素
     */
    public void print() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            System.out.print("下标位置[" + i + "]" + "\t");
            while (node != null) {
                System.out.print("key:" + node.getKey() + ",value:" + node.getValue() + "\t");
                node = node.next;
                /*if (node.next != null){
                    node = node.next;
                }else{
                    //结束循环
                    node = null;
                }*/
            }
            System.out.println();
        }
    }

    /**
     * 计算 hash 值指定下标位置
     *
     * @param k
     * @param length table 的长度
     * @return
     */
    public int getIndex(K k, int length) {
        int hashCode = k.hashCode();
        int hash = hashCode % length;
        return hash;
    }

    /**
     * 1.使用取模算法，定位数组链表
     *
     * @param k
     * @return
     */
    @Override
    public V get(K k) {
        //使用取模算法，定位数组链表
        int index = getIndex(k, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = getNode(table[index], k);
        return node == null ? null : node.getValue();
    }

    public Node<K, V> getNode(Node<K, V> node, K k) {
        while (node != null) {
            if (node.getKey().equals(k)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 对 table 进行扩容
     * 1.生成新的 table 是之前的两倍的大小
     * 2.重新计算 index 索引，存放在新的 table 里面
     * 3.将新的 newTable 赋值给老的 table
     */
    public void resize(){
        //生成新的 table 是之前的两倍的大小
        Node<K,V >[] newTable = new Node[DEFAULT_INITIAL_CAPACITY << 1];
        //重新计算 index 索引，存放在新的 table 里面
        for (int i = 0; i < table.length; i++) {
            //存放之前的 table 原来的 node
            Node<K, V> oldNode = table[i];
            while (oldNode != null){
                table[i] = null;//为了垃圾回收机制能够回收 将之前的 node 删除
                //存放之前的 table 原来的 node key
                K oldKey = oldNode.getKey();
                //重新计算 index
                int index = getIndex(oldKey, newTable.length);
                //存放之前的 table 原来的 node next
                Node<K, V> oldNext = oldNode.next;
                //将节点存放在新 table 链表的表头 如果 index 下表在新 newTable 发生相同的时候，以链表进行存储   原来的 node 的下一个是最新的（原来的node存放在新的 node 下一个）
                oldNode.next = newTable[index];
                //将之前的 node 赋值给 newTable[index]
                newTable[index] = oldNode;
                oldNode = oldNext;
            }
        }
        //将新的 newTable 赋值给老的 table
        table = newTable;
        DEFAULT_INITIAL_CAPACITY = newTable.length;
        newTable = null;//为了垃圾回收机制能够回收

    }

    /**
     * 定义节点 单链表
     *
     * @param <K>
     * @param <V>
     */
    class Node<K, V> implements Entry<K, V> {

        /**
         * 存放Map 结合 key
         */
        private K key;
        /**
         * 存放Map 结合 value
         */
        private V value;
        /**
         * 下一个节点
         */
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        /**
         * 设置新值返回老的值
         *
         * @param value 新的值
         * @return 老的值
         */
        @Override
        public V setValue(V value) {
            //老的值
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
