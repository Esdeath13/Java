package com.ywt.java;

/**
 * @author Finger
 * @file LinkNode.java
 * @CopyRight (C) https://spianmo.com
 * @brief
 * @email Finger@spianmo.com
 * @date 2020/10/01
 */
class LinkNode<E> {
    E data;
    LinkNode<E> next;

    public LinkNode(E d) {
        this.data = d;
        this.next = null;
    }

    public LinkNode() {
        this.next = null;
    }

    public LinkNode(E data, LinkNode<E> next) {
        this.next = next;
        this.data = data;
    }
}
