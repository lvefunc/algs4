import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] array;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++)
            copy[i] = array[i];

        array = copy;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size == array.length)
            resize(2 * array.length);
        array[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int index = StdRandom.uniformInt(size);

        Item item = array[index];
        array[index] = index == size - 1 ? null : array[size - 1];
        size--;

        if (size > 0 && size == array.length / 4)
            resize(array.length / 2);

        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int index = StdRandom.uniformInt(size);

        return array[index];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] copy;
        private int position;

        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[size];

            for (int i = 0; i < size; i++)
                copy[i] = array[i];

            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return position < copy.length;
        }

        public Item next() {
            if (position >= copy.length)
                throw new NoSuchElementException();

            return copy[position++];
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty())
            queue.enqueue(StdIn.readString());

        StdOut.printf("Initial randomized queue size: %d.%n", queue.size());

        while (!queue.isEmpty())
            StdOut.printf("Running dequeue operation: %s. Current size: %d.%n", queue.dequeue(), queue.size());
    }
}