package datastructure.linkedlist;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import datastructure.linkedlist.impl.LinkedList;
import datastructure.linkedlist.impl.LinkedListNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LinkedListTest {

    @Test
    void testAddAndGetNode() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(new LinkedListNode<>(1));
        list.add(new LinkedListNode<>(2));
        list.add(new LinkedListNode<>(3));

        // 노드 찾기
        assertNotNull(list.getNode(1));
        assertNotNull(list.getNode(2));
        assertNotNull(list.getNode(3));
        list.printAll();

        assertNull(list.getNode(4));  // 없는 값
    }

    @Test
    void testDeleteHead() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(new LinkedListNode<>(1));
        list.add(new LinkedListNode<>(2));
        list.add(new LinkedListNode<>(3));

        // head 삭제
        list.delete(1);
        list.printAll();

        assertNull(list.getNode(1));
        assertNotNull(list.getNode(2));
        assertNotNull(list.getNode(3));
    }

    @Test
    void testDeleteMiddle() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(new LinkedListNode<>(1));
        list.add(new LinkedListNode<>(2));
        list.add(new LinkedListNode<>(3));

        // 중간 값 삭제
        list.delete(2);
        list.printAll();

        assertNotNull(list.getNode(1));
        assertNull(list.getNode(2));
        assertNotNull(list.getNode(3));
    }

    @Test
    void testDeleteTail() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(new LinkedListNode<>(1));
        list.add(new LinkedListNode<>(2));
        list.add(new LinkedListNode<>(3));

        // tail 삭제
        list.delete(3);
        list.printAll();

        assertNotNull(list.getNode(1));
        assertNotNull(list.getNode(2));
        assertNull(list.getNode(3));
    }

    @Test
    void testDeleteNonExistingValue() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(new LinkedListNode<>(1));
        list.add(new LinkedListNode<>(2));

        // 없는 값 삭제 → 영향 없음
        list.delete(999);
        list.printAll();

        assertNotNull(list.getNode(1));
        assertNotNull(list.getNode(2));
    }

    @Test
    void testEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();

        // 아무 것도 없는 경우
        assertNull(list.getNode(1));
        list.delete(1);  // 예외 없어야 함
        list.printAll();

        assertNull(list.getNode(1));
    }

}
