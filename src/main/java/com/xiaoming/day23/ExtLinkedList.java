package com.xiaoming.day23;

public class ExtLinkedList<E> {
    /**
     * 实际大小
     */
    private int size;

    /**
     * 头结点
     */
    private Node first;
    /**
     * 尾结点
     */
    private Node last;

    public void add(E e){
        //创建新节点
        Node node = new Node();
        //给节点赋值
        node.object = e;
        if (first == null){
            first = node;
        }else {
            //存放上一个节点内容
            node.prev = last;
            //设置上一个节点的 next 为当前节点
            last.next = node;
        }
        last = node;
        size ++;
    }


    public Object get(int index){
        checkElementIndex(index);
        Node node = getNode(index);
        return node.object;
    }

    /**
     * 删除指定位置的节点
     * 1.获取指定节点
     * 2.获取指定节点的上一个节点
     * 3.获取指定节点的下一个节点
     * 4.指定节点的上一个节点的下一个节点指向指定节点的下一个节点
     * 5.指定节点的下一个节点的上一个节点指向指定节点的上一个节点
     * @param index
     */
    public void remove(int index){
        checkElementIndex(index);
        //获取指定节点
        Node node = getNode(index);
        if (node!=null){
            //获取指定节点的上一个节点
            Node oldNodePrev = node.prev;
            //获取指定节点的下一个节点
            Node oldNodeNext = node.next;
            if (oldNodePrev == null){
                first = oldNodeNext;
            } else {
                //指定节点的上一个节点的下一个节点指向指定节点的下一个节点
                oldNodePrev.next = oldNodeNext;
            }

            if (oldNodeNext == null){
                last = oldNodePrev;
            } else {
                //指定节点的下一个节点的上一个节点指向指定节点的上一个节点
                oldNodeNext.prev = oldNodePrev;
            }

            node = null;
            size --;
        }
    }

    /**
     * 往指定位置添加节点
     * 1.获取指定节点
     * 2.获取指定节点的上一个节点
     * 3.创建新节点 并赋值
     * 4.新节点的上一个节点为指定节点的上一个节点
     * 5.新节点的下一个节点为指定节点
     * 6.指定节点的上一个节点的下一个节点为新节点
     * 7.指定节点的上一个节点为新节点
     * @param index 索引
     * @param e
     */
    public void serAdd(int index, E e){
        //下标验证
        checkElementIndex(index);
        Node node = getNode(index);
        if (node!=null){
            //获取指定节点的上一个节点
            Node oldNodePrev = node.prev;
            //创建新节点
            Node newNode = new Node();
            newNode.object = e;

            if (oldNodePrev == null){
                first = newNode;
            }else {
                //新节点的上一个节点为指定节点的上一个节点
                newNode.prev = oldNodePrev;
                //指定节点的上一个节点的下一个节点为新节点
                oldNodePrev.next = newNode;
            }
            //新节点的下一个节点为指定节点
            newNode.next = node;
            //指定节点的上一个节点为新节点
            node.prev = newNode;
        }
        size ++;
    }

    public Node getNode(int index){
        checkElementIndex(index);
        Node node = null;
        if (first != null){
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    public int getSize(){
        return size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("下标越界 " + index);
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }


    private class Node{
        /**
         * 保存元素的数据
         */
        Object object;
        /**
         * 上一个节点
         */
        Node prev;
        /**
         * 下一个节点
         */
        Node next;
    }


    public static void main(String[] args) {
        ExtLinkedList<String> list = new ExtLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }

        list.remove(2);
        System.out.println("删除后");

        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }

        list.serAdd(0, "e");
        System.out.println("新增后");

        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }
    }
}
