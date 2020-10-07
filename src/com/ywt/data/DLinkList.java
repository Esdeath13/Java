package com.ywt.data;

/**
 * @ClassName LinkListClass
 * @Description TODO
 * @Author YWT
 * @Date 2020/10/7 15:10
 **/
public class DLinkList<E> {
    DLinkNode<E> mHead;

    public DLinkList() {
        mHead = new DLinkNode<E>();
        mHead.mFont = null;
        mHead.mNext = null;
    }

    public DLinkList(E[] input){
        mHead = new DLinkNode<E>();
        mHead.mFont = null;
        mHead.mNext = null;
        addAll(input);
    }

    public void addAll(E[] array) {
        DLinkNode<E> s, t;
        t = mHead;
        for (E e : array) {
            s = new DLinkNode<>(e);
            t.mNext = s;
            s.mFont = t;
            t = s;
        }
        t.mNext = null;
    }

    public void swap(DLinkNode<E> p, DLinkNode<E> q) {
        E tmp = p.mData;
        p.mData = q.mData;
        q.mData = tmp;
    }

    public boolean find(E x) {
        DLinkNode<E> p, pre;
        p = this.mHead.mNext;
        while (p != null && !p.mData.equals(x)){
            p = p.mNext;
        }
        if (p == null) {
            return false;
        }
        p.mFrequency++;
        pre = p.mFont;
        while (pre != this.mHead && pre.mFrequency < p.mFrequency)
        {
            swap(pre, p);
            p = pre;
            pre = p.mFont;
        }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        DLinkNode<E> p = mHead.mNext;
        while (p != null) {
            sb.append(" ");
            sb.append(p.mData.toString());
            sb.append(" ");
            p = p.mNext;
        }
        sb.append("}");
        return sb.toString();
    }


}
