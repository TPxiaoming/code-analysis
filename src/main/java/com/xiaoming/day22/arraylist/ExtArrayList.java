package com.xiaoming.day22.arraylist;

import java.util.Arrays;

public class ExtArrayList<E> implements ExtList<E>{

    /**
     * ArrayList 底层采用数组存放
     */
    private Object[] elementData;

    /**
     * 默认数组容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    /**
     * ArrayList 指定数组初始的容量
     *
     * @param initialCapacity 初始容量
     */
    public ExtArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("初始容量不能小于0");
        }
        elementData = new Object[initialCapacity];
    }

    /**
     * 默认初始容量为10
     * jdk1.7之后此代码放入add方法中
     */
    public ExtArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 线程安全问题
     * ArrayList 底层每次扩容是以1.5倍扩容
     *
     * @param e
     */
    public void add(E e) {
        //1.判断实际存放的数据容量是否大于 elementData 容量
        ensureCapacityInternal(size + 1);
        //2.使用下标进行赋值
        elementData[size++] = e;
    }

    public void add(int index, E e) {
        //1.判断实际存放的数据容量是否大于 elementData 容量
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index, elementData, index+1, size - index);
        elementData[index] = e;
    }

    /**
     * 判断实际存放的数据容量是否大于 elementData 容量
     * @param minCapacity 最小容量
     */
    private void ensureCapacityInternal(int minCapacity) {
        if (size == elementData.length) {
            //新数组容量大小
//            int newCapacity = size + size/2 ;

            //原来本身 elementData 容量大小
            int oldCapacity = elementData.length;
            //新数据容量
            int newCapacity = oldCapacity + (oldCapacity >> 1);

            //如果初始容量为1的时候，那么扩容容量为多少？
            //最少保证容量和和 minCapacity 一样
            if (newCapacity - minCapacity < 0)
                newCapacity = minCapacity;
            //将老数组的值赋值到新数组里面去
           /* Object[] newObjects = new Object[newCapacity];
            for (int i = 0; i < elementData.length; i++) {
                newObjects[i] = elementData[i];
            }*/
            Object[] newObjects = Arrays.copyOf(elementData, newCapacity);
            elementData = newObjects;
        }
    }


    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    E elementData(int index){
        return (E) elementData[index];
    }


    public E remove(int index){
        //1.使用下标查询该值是否存在
        E object = get(index);
        //计算删除元素后面的长度
        int numMoved = size - index -1;
        //2.删除原理 使用 arraycopy 往前移动数据，将最后一个变为空
        if (numMoved > 0)
             System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        //将最后一个元素变为空
        elementData[--size] = null;
        return  object;
    }

    /**
     * 删除元素相同区第一个
     * @param e
     * @return
     */
    public boolean remove(E e){
        for (int i = 0; i < size; i++) {
            Object value = elementData[i];
            if (value.equals(e)){
                remove(i);
                return true;
            }
        }

        return false;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("下标越界");
    }

    public int getSize() {
        return size;
    }
}
