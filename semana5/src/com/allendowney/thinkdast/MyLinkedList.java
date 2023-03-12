/**
 *
 */
package com.allendowney.thinkdast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author downey
 * @param <E>
 *
 */
public class MyLinkedList<E> implements List<E> {

    /**
     * Node is identical to ListNode from the example, but parameterized with T
     *
     * @author downey
     *
     */
    private class Node {
        public E data;
        public Node next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
        @SuppressWarnings("unused")
        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
        public String toString() {
            return "Node(" + data.toString() + ")";
        }
    }

    private int size;            // keeps track of the number of elements
    private Node head;           // reference to the first node

    /**
     *
     */
    public MyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // run a few simple tests
        List<Integer> mll = new MyLinkedList<Integer>();
        mll.add(1);
        mll.add(2);
        mll.add(3);
        mll.add(0, 4);
        mll.remove(1);
        System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());

        //mll.remove(2);
        //System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
    }

    @Override
    // El metodo add se podria mejorar para insertar al inicio
    public boolean add(E element) {
        var newNode = new Node(element);

        // LL esta vacia
        if (this.isEmpty()) {
            head = newNode;
        }
        // Insertar como ultimo nodo
        else {
            Node last = getNode(this.size - 1);
            last.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        var newNode = new Node(element);

        // insertar al inicio de la LL
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        }
        // insertar enmedio
        else {
            Node prev = this.getNode(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean flag = true;
        for (E element: collection) {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj: collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        Node node = getNode(index);
        return node.data;
    }

    /** Returns the node at the given index.
     * @param index
     * @return
     */
    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public int indexOf(Object target) {
        int index = 0;
        Node aux = head;
        while(aux != null) {
            if (target.equals(aux.data)) {
                return index;
            }
            index++;
            aux = aux.next;
        }
        return -1;
    }

    /** Checks whether an element of the array is the target.
     *
     * Handles the special case that the target is null.
     *
     * @param target
     * @param element
     */
    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        E[] array = (E[]) toArray();
        return Arrays.asList(array).iterator();
    }

    @Override
    public int lastIndexOf(Object target) {
        Node node = head;
        int index = -1;
        for (int i=0; i<size; i++) {
            if (equals(target, node.data)) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public E remove(int index) {
        E data;

        // remover el primer nodo
        if (index == 0) {
            head = head.next;
            data = head.data;
        }
        // remover un nodo interno
        else {
            Node prev = this.getNode(index - 1);
            data = prev.next.data;
            prev.next = prev.next.next;
        }
        size--;
        return data;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        Node node = getNode(index);
        E old = node.data;
        node.data = element;
        return old;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        // TODO: classify this and improve it.
        int i = 0;
        MyLinkedList<E> list = new MyLinkedList<E>();
        for (Node node=head; node != null; node = node.next) {
            if (i >= fromIndex && i <= toIndex) {
                list.add(node.data);
            }
            i++;
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node node=head; node != null; node = node.next) {
            // System.out.println(node);
            array[i] = node.data;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");

        Node aux = head;
        for(int i = 0; i < size; i++) {
            if (i == 0) {
                s.append(aux.data);
            } else {
                s.append(", " + aux.data);
            }
            aux = aux.next;
        }
        s.append("]");
        return s.toString();
    }
}
