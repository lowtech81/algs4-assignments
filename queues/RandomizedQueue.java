
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Item[] q;

    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item is null");
        if (size > 0 && size == q.length) {
            resize(q.length * 2);
        }
        q[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is empty");
        int ran = StdRandom.uniform(size);
        Item item = q[ran];
        q[ran] = q[--size];
        if (size > 0 && size == q.length / 4)
            resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("the queue is empty");
        int ran = StdRandom.uniform(size);
        return q[ran];
    }

    private void resize(int capability) {
        Item[] copy = (Item[]) new Object[capability];
        for (int i = 0; i < size; i++)
            copy[i] = q[i];
        q = copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        int[] order;
        int current;

        public RandomizedQueueIterator() {
            current = 0;
            order = new int[size];
            for (int i = 0; i < size; i++)
                order[i] = i;
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            return q[order[current++]];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
            StdOut.println("after enqueue " + i + " the size is " + rq.size());
        }
        StdOut.println(rq.sample());
        for (int i : rq) {
            StdOut.println(i);
        }
        for (int j : rq) {
            StdOut.println(j);
        }
    }
}
