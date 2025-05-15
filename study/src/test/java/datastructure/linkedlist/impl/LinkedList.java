package datastructure.linkedlist.impl;

import java.util.Objects;

public class LinkedList<T> {
    private LinkedListNode<T> next;

    public LinkedList() {
        next = null;
    }

    public void add(LinkedListNode<T> node) {
        if (next == null) {
            this.next = node;
            return;
        }
        next.addNext(node);
    }

    public void delete(T value) {
        if (next == null) {
            return;
        }
        if (Objects.equals(next.getValue(), value)) {
            this.next = this.next.getNext();
        } else {
            this.next.deleteValue(value);
        }
    }

    public LinkedListNode<T> getNode(T value) {
        if (next == null) {
            return null;
        }
        if (Objects.equals(next.getValue(), value)) {
            return next;
        } else {
            return next.getNode(value);
        }
    }

    public void printAll() {
        if (next == null) {
            return;
        }
        next.printAll();
    }
}
