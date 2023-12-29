import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[] sites;
    private final WeightedQuickUnionUF virtualTop;
    private final WeightedQuickUnionUF virtualTopBot;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n is less than 0");
        else
            this.n = n;

        int size = n * n;

        sites = new boolean[size];
        virtualTop = new WeightedQuickUnionUF(size + 1);
        virtualTopBot = new WeightedQuickUnionUF(size + 2);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("row value is out of range");
        if (col < 1 || col > n)
            throw new IllegalArgumentException("column value is out of range");
    }

    private int indexOf(int row, int col) {
        validate(row, col);

        return (row - 1) * n + (col - 1);
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);

        return sites[indexOf(row, col)];
    }

    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col))
            return;

        sites[indexOf(row, col)] = true;
        numberOfOpenSites++;

        // virtual virtualTop site
        if (row == 1) {
            virtualTop.union(indexOf(row, col), n * n);
            virtualTopBot.union(indexOf(row, col), n * n);
        }

        // virtual bottom site
        if (row == n) {
            virtualTopBot.union(indexOf(row, col), n * n + 1);
        }

        // neighbor sites
        if (row != 1 && isOpen(row - 1, col)) {
            virtualTop.union(indexOf(row - 1, col), indexOf(row, col));
            virtualTopBot.union(indexOf(row - 1, col), indexOf(row, col));
        }

        if (col != n && isOpen(row, col + 1)) {
            virtualTop.union(indexOf(row, col + 1), indexOf(row, col));
            virtualTopBot.union(indexOf(row, col + 1), indexOf(row, col));
        }

        if (row != n && isOpen(row + 1, col)) {
            virtualTop.union(indexOf(row + 1, col), indexOf(row, col));
            virtualTopBot.union(indexOf(row + 1, col), indexOf(row, col));
        }

        if (col != 1 && isOpen(row, col - 1)) {
            virtualTop.union(indexOf(row, col - 1), indexOf(row, col));
            virtualTopBot.union(indexOf(row, col - 1), indexOf(row, col));
        }
    }

    public boolean isFull(int row, int col) {
        validate(row, col);

        return virtualTop.find(n * n) == virtualTop.find(indexOf(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return virtualTopBot.find(n * n) == virtualTopBot.find(n * n + 1);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(StdIn.readInt());

        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }

        StdOut.printf("Percolates: %b.%nNumber of open sites: %d.%n", percolation.percolates(), percolation.numberOfOpenSites());
    }
}
