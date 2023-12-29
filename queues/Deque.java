import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        Node prev;
        Node next;
        Item item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node newfirst = new Node();

        if (first != null) {
            first.prev = newfirst;
            newfirst.next = first;
        }

        newfirst.prev = null;
        newfirst.item = item;

        if (last == null)
            last = newfirst;

        first = newfirst;
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node newlast = new Node();

        if (last != null) {
            last.next = newlast;
            newlast.prev = last;
        }

        newlast.next = null;
        newlast.item = item;

        if (first == null)
            first = newlast;

        last = newlast;
        size++;
    }

    private Item removeIfSizeIsOneElement() {
        Item item = first.item;

        first = null;
        last = null;
        size--;

        return item;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (first == last)
            return removeIfSizeIsOneElement();

        Node oldfirst = first;
        first = oldfirst.next;
        first.prev = null;
        size--;

        return oldfirst.item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (first == last)
            return removeIfSizeIsOneElement();

        Node oldlast = last;
        last = oldlast.prev;
        last.next = null;
        size--;

        return oldlast.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node pointer;
        private int position;

        private DequeIterator() {
            pointer = first;
        }

        public boolean hasNext() {
            return position < size();
        }

        public Item next() {
            if (pointer == null)
                throw new NoSuchElementException();

            Item item = pointer.item;

            pointer = pointer.next;
            position++;

            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        while (!StdIn.isEmpty())
            if (StdRandom.bernoulli(0.5))
                deque.addFirst(StdIn.readString());
            else
                deque.addLast(StdIn.readString());

        StdOut.printf("Initial deque size: %d.%n", deque.size());

        while (!deque.isEmpty())
            if (StdRandom.bernoulli(0.5))
                StdOut.printf("Removing first: %s. Current size: %d.%n", deque.removeFirst(), deque.size());
            else
                StdOut.printf("Removing last: %s. Current size: %d.%n", deque.removeLast(), deque.size());
    }
}