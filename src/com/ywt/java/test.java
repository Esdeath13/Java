package com.ywt.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Finger
 * @file test3.java
 * @CopyRight (C) https://spianmo.com
 * @brief
 * @email Finger@spianmo.com
 * @date 2020/10/01
 */
public class test {
    public static final int POW10_6 = 1000000;

    /**
     * 删除整型单向链表中的所有最大值结点
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        System.out.println("===========>请输入测试用例以初始化单向链表,输入exit退出");
        while (true) {
            String input_int = scanner.next();
            if (input_int.equals("exit")) {
                break;
            }
            list.add(Integer.valueOf(input_int));
        }
        Integer[] array = list.toArray(new Integer[0]);
        testRemoveAllMaxNode(array);
    }

    /**
     * @param cases Integer[] 用于初始化单向链表的Integer类型数组（测试用例）
     */
    public static void testRemoveAllMaxNode(Integer[] cases) {
        /**
         * 最直接能想到的办法，两步走，先遍历链表得到最大值，然后再遍历一次判断遍历的每一项与最大项的大小关系，相等则进行删除操作（前置
         * Node的next指向当前最大项的next）。
         * 长度11的链表测试用例平均耗时: 0.096毫秒
         */
        IntegerLinkListClass<Integer> linkList1 = new IntegerLinkListClass<>(cases);
        long preMills = System.nanoTime();
        removeAllMaxNodeCommon(linkList1);
        System.out.println("================>Array两次循环 实现耗时" + (double) (System.nanoTime() - preMills) / POW10_6 + "毫秒");

        /**
         * 封装删除操作的单元（包含preMaxNode和MaxNode结点的removeActionUnit对象），使用Array暂存删除单元，使用一层while循环遍历，
         * 遍历完成后foreach处理removeActionUnit[]单元，虽然理论上讲耗时应该少于第一种方法，但是由于多了一些判断语句和数组的操作导致时间增加。
         * 长度11的链表测试用例平均耗时: 0.684毫秒
         */
        IntegerLinkListClass<Integer> linkList2 = new IntegerLinkListClass<>(cases);
        preMills = System.nanoTime();
        removeAllMaxNodeUseArray(linkList2);
        System.out.println("================>Array单循环 实现耗时" + (double) (System.nanoTime() - preMills) / POW10_6 + "毫秒");

        /**
         * 封装删除操作的单元（包含preMaxNode和MaxNode结点的removeActionUnit对象），使用ArrayList暂存删除单元，使用一层while循环遍
         * 历，遍历完成后foreach处理ArrayList<removeActionUnit>()单元，时间复杂度平均也为O((3/2)n)，但是由于使用了JDK基于数组封装的
         * ArrayList，算法整体耗时比上一种缩短好几倍且略优于第一种两步走算法。
         * 长度11的链表测试用例平均耗时: 0.0843毫秒
         */
        IntegerLinkListClass<Integer> linkList3 = new IntegerLinkListClass<>(cases);
        preMills = System.nanoTime();
        removeAllMaxNodeUseArrayList(linkList3);
        System.out.println("================>ArrayList单循环 实现耗时" + (double) (System.nanoTime() - preMills) / POW10_6 + "毫秒");

        /**
         * 耗时最短，创建IntegerLinkListClass继承自LinkListClass对象，在其构造函数中增加addNodeListener接口，并当初始化单向
         * 链表时通过addNodeListener接口回调，并在其onNodeAdded(E node)回调方法中实现Integer的大小值比对，这样初始化单向链表完毕我们
         * 就已经得到了此Integer类型单向链表的最大值和最小值，这样我们便可以避免额外的遍历查找最大值的操作，因为在IntegerLinkListClass
         * 中我们已经实现了，然后递归removeNodeByData删除.data相等的Node节点。
         * 长度11的链表测试用例平均耗时: 0.0363毫秒
         */
        IntegerLinkListClass<Integer> linkList4 = new IntegerLinkListClass<>(cases);
        preMills = System.nanoTime();
        linkList4.removeAllNodeByData(linkList4.maxValue());
        System.out.println("===>>>>>" + linkList4);
        System.out.println("================>构造函数预处理 实现耗时" + (double) (System.nanoTime() - preMills) / POW10_6 + "毫秒");
    }

    static class removeActionUnit {
        public LinkNode<Integer> maxPtr;
        public LinkNode<Integer> maxPrePtr;

        public removeActionUnit() {
        }

        public removeActionUnit(LinkNode<Integer> maxPtr, LinkNode<Integer> maxPrePtr) {
            this.maxPtr = maxPtr;
            this.maxPrePtr = maxPrePtr;
        }

        public LinkNode<Integer> getMaxPtr() {
            return maxPtr;
        }

        public void setMaxPtr(LinkNode<Integer> maxPtr) {
            this.maxPtr = maxPtr;
        }

        public LinkNode<Integer> getMaxPrePtr() {
            return maxPrePtr;
        }

        public void setMaxPrePtr(LinkNode<Integer> maxPrePtr) {
            this.maxPrePtr = maxPrePtr;
        }
    }

    public static void removeAllMaxNodeCommon(IntegerLinkListClass<Integer> list) {
        LinkNode<Integer> p = list.head.next;
        Integer maxValue = p.data;
        while (p.next != null) {
            if (p.next.data > maxValue) {
                maxValue = p.next.data;
            }
            p = p.next;
        }
        LinkNode<Integer> preNode = list.head;
        p = preNode.next;
        while(p != null){
            if (maxValue.equals(p.data)) {
                preNode.next = p.next;
            }else{
                preNode =  preNode.next;
            }
            p = preNode.next;
        }
        System.out.println("===>>>>>" + list.toString());
    }

    public static void removeAllMaxNodeUseArray(IntegerLinkListClass<Integer> list) {
        removeActionUnit[] removeActionUnits = new removeActionUnit[list.size()];
        int actionCount = 0;
        LinkNode<Integer> p = list.head;
        LinkNode<Integer> maxPtr = p.next;
        LinkNode<Integer> maxPrePtr = p;
        int count = 0;
        while (p.next != null) {
            if (p.next.data > maxPtr.data) {
                maxPtr = p.next;
                maxPrePtr = p;
                removeActionUnits = null;
                removeActionUnits = new removeActionUnit[list.size()];
                count = 1;
                actionCount = 0;
            } else if (p.next.data.equals(maxPtr.data)) {
                maxPtr = p.next;
                maxPrePtr = p;
                count++;
            } else if (p.next.data < maxPtr.data) {
                count = 0;
            }

            if (count == 1) {
                removeActionUnit tmp = new removeActionUnit();
                tmp.maxPtr = maxPtr;
                tmp.maxPrePtr = maxPrePtr;
                removeActionUnits[actionCount] = tmp;
                ++actionCount;
            } else if (count != 0) {
                removeActionUnits[actionCount - 1].setMaxPtr(maxPtr);
            }
            p = p.next;
        }
        for (int i = 0; i < actionCount; i++) {
            removeActionUnits[i].maxPrePtr.next = removeActionUnits[i].maxPtr.next;
        }

        System.out.println("===>>>>>" + list.toString());
    }

    public static void removeAllMaxNodeUseArrayList(IntegerLinkListClass<Integer> list) {
        List<removeActionUnit> removeActionUnits = new ArrayList<>();
        LinkNode<Integer> p = list.head;
        LinkNode<Integer> maxPtr = p.next;
        LinkNode<Integer> maxPrePtr = p;
        int count = 0;
        while (p.next != null) {
            if (p.next.data > maxPtr.data) {
                maxPtr = p.next;
                maxPrePtr = p;
                removeActionUnits.clear();
                count = 1;
            } else if (p.next.data.equals(maxPtr.data)) {
                maxPtr = p.next;
                maxPrePtr = p;
                count++;
            } else if (p.next.data < maxPtr.data) {
                count = 0;
            }
            if (count == 1) {
                removeActionUnit tmp = new removeActionUnit();
                tmp.maxPtr = maxPtr;
                tmp.maxPrePtr = maxPrePtr;
                removeActionUnits.add(tmp);
            } else if (count != 0) {
                removeActionUnits.get(removeActionUnits.size() - 1).setMaxPtr(maxPtr);
            }
            p = p.next;
        }

        for (removeActionUnit r : removeActionUnits) {
            r.maxPrePtr.next = r.maxPtr.next;
        }
        System.out.println("===>>>>>" + list.toString());
    }

    public static void removeAllMaxNodeUseSelf(IntegerLinkListClass<Integer> list) {
        list.removeAllNodeByData(list.maxValue());
    }

}