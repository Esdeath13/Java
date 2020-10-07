package com.ywt.data;
import java.util.*;

/**
 * @ClassName DLink
 * @Description TODO
 * @Author YWT
 * @Date 2020/10/7 14:38
 **/

public class DLink {
    public static void main(String[] args) {
        Integer[] input = {1, 2, 3, 4, 5, 6};
        DLinkList<Integer> list = new DLinkList<>(input);
        System.out.println(list);
        list.find(4);
        list.find(6);
        list.find(4);
        list.find(4);
        list.find(4);
        System.out.println(list);
    }
}
