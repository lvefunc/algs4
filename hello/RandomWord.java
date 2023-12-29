import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String champion = null;

        while (!StdIn.isEmpty()) {
            String current = StdIn.readString();

            double temp = 1.0 / (double) i;

            if (StdRandom.bernoulli(1.0 / (double) i)) {
                champion = current;
            }

            i++;
        }

        StdOut.println(champion);
    }
}
