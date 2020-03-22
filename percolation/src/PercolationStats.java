import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n;
    private final int trials;
    private final int[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0)
            throw new IllegalArgumentException("n and trails must greater than zero.");

        this.n = n;
        this.trials = trials;
        results = new int[trials];
        Percolation perc;
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            results[i] = perc.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {

        return StdStats.mean(results) / (n * n);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results) / (n * n);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }

}
