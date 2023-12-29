import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty())
            queue.enqueue(StdIn.readString());

        for (int i = 0; i < Integer.parseInt(args[0]); i++)
            StdOut.println(queue.dequeue());
    }
}
