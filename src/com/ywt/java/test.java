package com.ywt.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        System.out.println("请输入案例,输入exit退出");
        while (true) {
            String input_int = scanner.next();
            if (input_int.equals("exit")) {
                break;
            }
            list.add(Integer.valueOf(input_int));
        }
        Integer[] array = list.toArray(new Integer[0]);
        testRemoveMaxNode(array);
    }


    public static void testRemoveMaxNode(Integer[] cases) {

        IntegerLinkListClass<Integer> linkList1 = new IntegerLinkListClass<>(cases);
        long preMills = System.nanoTime();
        removeMaxNodeCommon(linkList1);


    }

    static class removeActionUnit {
        public LinkNode<Integer> maxPtr;
        public LinkNode<Integer> maxExPtr;

        public removeActionUnit() {
        }

        public removeActionUnit(LinkNode<Integer> maxPtr, LinkNode<Integer> maxExPtr) {
            this.maxPtr = maxPtr;
            this.maxExPtr = maxExPtr;
        }

        public LinkNode<Integer> getMaxPtr() {
            return maxPtr;
        }

        public void setMaxPtr(LinkNode<Integer> maxPtr) {
            this.maxPtr = maxPtr;
        }

        public LinkNode<Integer> getMaxPrePtr() {
            return maxExPtr;
        }

        public void setMaxPrePtr(LinkNode<Integer> maxExPtr) {
            this.maxExPtr = maxExPtr;
        }
    }

    public static void removeMaxNodeCommon(IntegerLinkListClass<Integer> list) {
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
        System.out.println(list.toString());
    }

    public static void removeMaxNodeUseArray(IntegerLinkListClass<Integer> list) {
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
                tmp.maxExPtr = maxPrePtr;
                removeActionUnits[actionCount] = tmp;
                ++actionCount;
            } else if (count != 0) {
                removeActionUnits[actionCount - 1].setMaxPtr(maxPtr);
            }
            p = p.next;
        }
        for (int i = 0; i < actionCount; i++) {
            removeActionUnits[i].maxExPtr.next = removeActionUnits[i].maxPtr.next;
        }

        System.out.println(list.toString());
    }

    public static void removeMaxNodeUseArrayList(IntegerLinkListClass<Integer> list) {
        List<removeActionUnit> removeActionUnits = new ArrayList<>();
        LinkNode<Integer> p = list.head;
        LinkNode<Integer> maxPtr = p.next;
        LinkNode<Integer> maxExPtr = p;
        int count = 0;
        while (p.next != null) {
            if (p.next.data > maxPtr.data) {
                maxPtr = p.next;
                maxExPtr = p;
                removeActionUnits.clear();
                count = 1;
            } else if (p.next.data.equals(maxPtr.data)) {
                maxPtr = p.next;
                maxExPtr = p;
                count++;
            } else if (p.next.data < maxPtr.data) {
                count = 0;
            }
            if (count == 1) {
                removeActionUnit tmp = new removeActionUnit();
                tmp.maxPtr = maxPtr;
                tmp.maxExPtr = maxExPtr;
                removeActionUnits.add(tmp);
            } else if (count != 0) {
                removeActionUnits.get(removeActionUnits.size() - 1).setMaxPtr(maxPtr);
            }
            p = p.next;
        }

        for (removeActionUnit r : removeActionUnits) {
            r.maxExPtr.next = r.maxPtr.next;
        }
        System.out.println(list.toString());
    }


    }
