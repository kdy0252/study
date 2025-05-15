package datastructure.linkedlist.impl;

import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class LinkedListNode<T> {
    private final T value;
    private LinkedListNode<T> next;

    public LinkedListNode(T value) {
        this.value = value;
        this.next = null;
    }

    public void addNext(LinkedListNode<T> next) {
        if (this.next == null) {
            this.next = next;
            return;
        }

        this.next.addNext(next);
    }

    public void deleteValue(T value) {
        if (this.next != null && Objects.equals(this.next.getValue(), value)) {
            this.next = this.next.next;
        } else if (this.next != null) {
            this.next.deleteValue(value);
        }
    }

    public LinkedListNode<T> getNode(T value) {
        if (Objects.equals(this.value, value)) {
            return this;
        }
        if (next == null) {
            return null;
        }
        return next.getNode(value);
    }

    public void printAll() {
        log.info("value: {}", value.toString());
        if (next != null) {
            next.printAll();
        }
    }
}
