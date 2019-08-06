package com.xiaoming.day22.arraylist;

/**
 * 自定义 list 接口
 * @param <E>
 */
public interface ExtList<E> {
    public void add(E e);

    public void add(int index, E e);

    public E get(int index);

    public E remove(int index);

    public boolean remove(E e);
}
