import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("n is less or equal to 0");
        if (trials <= 0)
            throw new IllegalArgumentException("trials is less or equal to 0");

        double[] fractions = new double[trials];

        for (int i = 0; i < trials; i++)
            fractions[i] = simulate(n);

        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
        confidenceLo = mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
    }

    private double simulate(int n) {
        int size = n * n;
        int open = 0;

        Percolation percolation = new Percolation(n);
        boolean[] sites = new boolean[size];

        while (!percolation.percolates()) {
            int pos = StdRandom.uniformInt(size - open);
            int current = 0;

            for (int i = 0; i < size; i++)
                if (!sites[i])
                    if (current != pos) {
                        current++;
                    } else {
                        percolation.open(i / n + 1, i % n + 1);
                        open++;
                        sites[i] = !sites[i];
                        break;
                    }
        }

        return (double) open / size;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        double mean = percolationStats.mean();
        double stddev = percolationStats.stddev();
        double confidenceLo = percolationStats.confidenceLo();
        double confidenceHi = percolationStats.confidenceHi();

        StdOut.printf("mean                    = %.10f%n", mean);
        StdOut.printf("stddev                  = %.10f%n", stddev);
        StdOut.printf("95%% confidence interval = [%1$.10f, %2$.10f]%n", confidenceLo, confidenceHi);
    }
}
