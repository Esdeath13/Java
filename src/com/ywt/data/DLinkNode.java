package com.ywt.data;

/**
 * @ClassName LinkNode
 * @Description TODO
 * @Author YWT
 * @Date 2020/10/7 15:07
 **/
public class DLinkNode<E> {
    E mData;
    int mFrequency;
    DLinkNode<E> mFont;
    DLinkNode<E> mNext;

    public DLinkNode(){
        mFrequency = 0;
        mFont = null;
        mNext = null;
    }

    public DLinkNode(E data){
        mFrequency = 0;
        mData = data;
        mNext = null;
    }
}
